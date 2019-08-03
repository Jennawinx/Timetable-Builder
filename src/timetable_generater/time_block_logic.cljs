(ns timetable-generater.time-block-logic)

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

(def slot1 {:main-label   "DD"
            :abbreviation "D"
            :group        "DD"
            :column       "monday"
            :start-time   15
            :end-time     18
            :optional     {:type "apple"}})

(defn time-overlap? [[s1 e1] [s2 e2]]
  (or (and (< s1 s2) (< s2 e1))                             ;; second overlaps tail of first
      (and (< s2 s1) (< s1 e2)))                            ;; first overlaps tail of second
  )




(defn insert-slot-to-column [column-slots {new-slot-start :start-time
                                           new-slot-end   :end-time
                                           :as            new-slot}]
  )

(defn add-time-slot [slots {{column :column} :required :as slot}]
  (update slots column insert-slot-to-column slot))

