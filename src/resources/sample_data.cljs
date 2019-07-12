(ns resources.sample-data)

(def sample-data
  {
   ;; default styling
   :groups             {"CSCA08" {:color "black"}
                        "MATA31" {}}

   :labels             {"room" {:bold      true
                                :font-size "0.8em"}
                        "type" {:uppercase true}}

   :cell-views         {}
   :table-views        {}

   ;; if we rename, we don't have to change references
   :col-name->id       {"monday"    1001
                        "tuesday"   1002
                        "wednesday" 1004}

   :cell-views->id     {}

   :table-views->id    {"default" {:display-days [1004 1001 1002]
                                   :width        "86vh"
                                   :height       "100vh"
                                   :increment    1}}

   ;; active
   :current-cell-view  :default
   :current-table-view :default

   ;; data
   :slots              {1001 [{:main-label   "CSCA08"
                               :abbreviation "A08"
                               :group        "CSCA08"
                               :column       1001
                               :start-time   10
                               :end-time     12
                               :type         "lec"}]
                        1002 [{:conflict true
                               ;:start-time 9                                                                              ;; ref # min items time (calculated on demand)
                               ;:end-time   11                                                                             ;; ref # max items time (calculated on demand)
                               :items    [{:main-label "CSCA08H3"
                                           :group      "CSCA08"
                                           :column     1002
                                           :start-time 9
                                           :end-time   10
                                           :type       "tut"}
                                          {:main-label "MATA31H3"
                                           :group      "MATA31"
                                           :column     1002
                                           :start-time 9
                                           :end-time   11
                                           :type       "lec"}]}]}

   })

"
Notes,
when time or date changes, remove old and insert new slot

Groups
are created when new name group name is added manually or during slot creation
are deleted manually
references to missing group styles goes to default styles

on delete
remove slot from slots,
resolve conflict if needed

removing columns does not remove column data, highlight hanging data
"