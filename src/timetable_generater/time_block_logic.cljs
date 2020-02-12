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


(defn update-conflict [{:keys [start-time end-time conflict? items] :as slot1}
                       {s2         :start-time
                        e2         :end-time
                        conflict2? :conflict?
                        items2     :items
                        :as        slot2}]
  {:conflict?  true
   :start-time (min start-time s2)
   :end-time   (max end-time e2)
   :items      (cond (and conflict? conflict2?)
                     (concat items items2)

                     conflict?
                     (conj items slot2)

                     conflict2?
                     (conj items2 slot1)

                     :else
                     [slot1 slot2])})


(defn insert-slot [{new-slot-start :start-time new-slot-end :end-time :as new-slot}]
  (fn [[inserted? result {s1 :start-time e1 :end-time :as slot1}]
       {s2 :start-time e2 :end-time :as slot2}]
    (cond (nil? slot1)
          [false [] slot2]

          (and inserted? (time-overlap? [s1 e1] [s2 e2]))
          [true result (update-conflict slot1 slot2)]

          inserted?
          [true (conj result slot1) slot2]

          :else
          (let [orders [(time-order [new-slot-start new-slot-end] [s1 e1]) (time-order [new-slot-start new-slot-end] [s2 e2])]]
            (println orders "inserting" [new-slot-start new-slot-end] "|" [s1 e1] "|" [s2 e2])
            (case orders
              ;; n < a < b
              [1 1] [true (conj result new-slot slot1) slot2]
              ;; a~n < b
              [0 1] [true (conj result (update-conflict new-slot slot1)) slot2]
              ;; a~n~b
              [0 0] [true result (-> (update-conflict new-slot slot1)
                                     (update-conflict slot2))]
              ;; a < n < b
              [-1 1] [true (conj result slot1 new-slot) slot2]
              ;; a < n~b
              [-1 0] [true (conj result slot1) (update-conflict new-slot slot2)]
              ;; a < b < n
              [-1 -1] [false (conj result slot1) slot2])))))


(defn insert-slot-to-column [column-slots {new-slot-start :start-time
                                           new-slot-end   :end-time
                                           :as            new-slot}]
  (if (empty? column-slots)
    [new-slot]
    (let [[inserted? result {:keys [start-time end-time] :as last-slot}]
          (reduce (insert-slot new-slot)
                  [false [] nil]
                  column-slots)]
      (cljs.pprint/pprint [inserted? result last-slot])
      (if inserted?
        (conj result last-slot)
        (case (time-order [new-slot-start new-slot-end] [start-time end-time])
          1 (conj result new-slot last-slot)
          0 (conj result (update-conflict last-slot new-slot))
          -1 (conj result last-slot new-slot))))))

(defn add-time-slot [slots {:keys [column] :as slot}]
  (update slots column insert-slot-to-column slot))


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

(add-time-slot sample-slots slot1)
(add-time-slot sample-slots slot2)

(add-time-slot sample-slots {:main-label   "BBBBBB"
                             :abbreviation "BBB"
                             :group        "BBBBBB"
                             :column       "monday"
                             :start-time   14
                             :end-time     17
                             :optional     {:type "apple"}})