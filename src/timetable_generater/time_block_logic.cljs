(ns timetable-generater.time-block-logic
  (:require [timetable-generater.utils :as utils]))


(defn time-overlap? [[s1 e1] [s2 e2]]
  (or (and (< s1 s2) (< s2 e1))                             ;; second overlaps tail of first
      (and (< s2 s1) (< s1 e2)))                            ;; first overlaps tail of second
  )

(defn time-order
  "1 before
  0 conflict
  -1 after"
  ([[s1 e1] [s2 e2]]
   (cond
     (< s1 s2)
     (if (< s2 e1)
       0
       1)
     (< s2 s1)
     (if (< s1 e2)
       0
       -1)
     :else 0)))

(defn update-conflict [{:keys [start-time end-time conflict? items] :as conflict}
                       {new-slot-start :start-time new-slot-end :end-time :as new-slot}]
  {:conflict?  true
   :start-time (min start-time new-slot-start)
   :end-time   (max end-time new-slot-end)
   :items      (if conflict?
                 (conj items new-slot)
                 [conflict new-slot])})

(declare insert-slot-to-column)

(defn insert-slot [{new-slot-start :start-time new-slot-end :end-time :as new-slot}]
  ;; TODO ERRORS Tail Conflicts Can Clash with more than 1
  ;; TODO algorithm tweaks, rather than positive lookahead, should lookbehind instead and combine conflicts, no short circuiting
  (fn [result
       {s1 :start-time e1 :end-time :as slot1}
       {s2 :start-time e2 :end-time :as slot2}]
    (let [order-with-first  (time-order [new-slot-start new-slot-end] [s1 e1])
          order-with-second (time-order [new-slot-start new-slot-end] [s2 e2])
          new-result        (->> (case order-with-first
                                   ;; n < a < b
                                   1 [new-slot slot1 slot2]

                                   ;; a~n
                                   0 (case order-with-second
                                       ;; a~n < b
                                       1 [(update-conflict slot1 new-slot) slot2]
                                       ;; a~n~b
                                       0 (-> (update-conflict slot1 new-slot)
                                             (update-conflict slot2)
                                             (vec)))
                                   ;; a < n
                                   -1 (case order-with-second
                                        ;; a < n < b
                                        1 [slot1 new-slot slot2]
                                        ;; a < n~b
                                        0 [slot1 (update-conflict slot2 new-slot)]
                                        ;; a < b < n
                                        -1 [slot1 slot2]))
                                 (concat result))]
      (println "orders" order-with-first order-with-second)
      (if (= -1 order-with-first order-with-second)
        new-result
        {:break! new-result}))))

(defn insert-slot-to-column [column-slots {new-slot-start :start-time
                                           new-slot-end   :end-time
                                           :as            new-slot}]
  (case (count column-slots)
    0 [new-slot]
    1 (let [{:keys [start-time end-time] :as slot1} (first column-slots)]
        (println "single")
        (case (time-order [new-slot-start new-slot-end] [start-time end-time])
          1 [new-slot slot1]
          0 [(update-conflict slot1 new-slot)]
          -1 [slot1 new-slot]))
    (do
      (println "multiple")
      (utils/reduce-lazy-lookahead (insert-slot new-slot) [] column-slots))))

(defn add-time-slot [slots {:keys [column] :as slot}]
  (update slots column insert-slot-to-column slot))

;; TODO more testing

(def sample-slots {"monday"  [{:main-label   "CSCA08"
                               :abbreviation "A08"
                               :group        "CSCA08"
                               :column       "monday"
                               :start-time   10
                               :end-time     12
                               :optional     {:type "lec"}}
                              {:main-label   "AAAAAA"
                               :abbreviation "AAA"
                               :group        "AAAAAA"
                               :column       "monday"
                               :start-time   16
                               :end-time     18
                               :optional     {:type "apple"}}]

                   "tuesday" [{:conflict   true
                               :start-time 9                ;; ref # min items time (calculated on demand)
                               :end-time   11               ;; ref # max items time (calculated on demand)
                               :items      [{:main-label   "CSCA08H3"
                                             :abbreviation "A08"
                                             :group        "CSCA08"
                                             :column       "tuesday"
                                             :start-time   9
                                             :end-time     10
                                             :optional     {:type "tut"}}

                                            {:main-label   "MATA31H3"
                                             :abbreviation "A31"
                                             :group        "MATA31"
                                             :column       "tuesday"
                                             :start-time   9
                                             :end-time     11
                                             :optional     {:type "lec"}}]}]})

(def slot1 {:main-label   "BBBBBB"
            :abbreviation "BBB"
            :group        "BBBBBB"
            :column       "monday"
            :start-time   14
            :end-time     16
            :optional     {:type "apple"}})

(def slot2 {:main-label   "DD"
            :abbreviation "D"
            :group        "DD"
            :column       "monday"
            :start-time   15
            :end-time     18
            :optional     {:type "apple"}})

#_(add-time-slot sample-slots slot1)
