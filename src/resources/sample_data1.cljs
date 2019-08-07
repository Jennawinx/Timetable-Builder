(ns resources.sample-data1)

(def sample-data
  {
   ;; Auto-fill
   :main-labels        {"Intro to Comp Sci 1" "CSCA08"
                        "Calc 1"              "MATA31H3"
                        "Hello"               "hi"}
   :groups             #{"CSCA08" "MATA31"}
   :labels             #{"room" "type"}

   ;; styling
   :themes             {"default" {:groups {"CSCA08" "skyblue"
                                            "MATA31" "teal"}}}

   :cell-views         {"default" [:div
                                   [:p "{%main-label%}"]
                                   [:p "{%type%}"]]}

   :table-views        {"default" {:display-days ["wednesday" "monday" "tuesday"]
                                   :width        "86vh"
                                   :height       "100vh"
                                   :increment    1
                                   :min-time     8
                                   :max-time     20}}

   ;; active
   :current-theme      "default"
   :current-table-view "default"

   ;; data
   :slots              {"monday"  [{:required {:main-label   "CSCA08"
                                               :abbreviation "A08"
                                               :group        "CSCA08"
                                               :column       "monday"
                                               :start-time   10
                                               :end-time     12}
                                    :optional {:type "lec"}}]

                        "tuesday" [{:conflict?  true
                                    :start-time 9           ;; ref # min items time (calculated on demand)
                                    :end-time   11          ;; ref # max items time (calculated on demand)
                                    :items      [{:required {:main-label   "CSCA08H3"
                                                             :abbreviation "A08"
                                                             :group        "CSCA08"
                                                             :column       "tuesday"
                                                             :start-time   9
                                                             :end-time     10}
                                                  :optional {:type "tut"}}

                                                 {:required {:main-label   "MATA31H3"
                                                             :abbreviation "A31"
                                                             :group        "MATA31"
                                                             :column       "tuesday"
                                                             :start-time   9
                                                             :end-time     11}
                                                  :optional {:type "lec"}}]}]}
   })
