(ns timetable-generater.time-block-logic)

(def sample-slots {"monday"  [{:main-label   "CSCA08"
                               :abbreviation "A08"
                               :group        "CSCA08"
                               :column       "monday"
                               :start-time   10
                               :end-time     12
                               :optional     {:type "lec"}}]

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


(defn insert-slot-to-column [column-slots {:keys [start-time end-time] :as slot}]
  )

(defn add-time-slot [slots {{column :column} :required :as slot}]
  (update slots column insert-slot-to-column slot))

