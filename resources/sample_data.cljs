(ns sample-data)

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

   :cell-views         {"default" [:div.slot.info
                                   [:div {:style {:font-weight :bold
                                                  :text-transform :uppercase}}
                                    "{%abbreviation%} {%type%} {%number%}"]
                                   [:div {:style {:text-transform :uppercase}}
                                    "{%room%}"]]}

   :table-views        {"default" {:display-days ["wednesday" "monday" "tuesday"]
                                   :width        "86vh"
                                   :height       "100vh"
                                   :increment    1
                                   :min-time     8
                                   :max-time     20}}

   ;; data
   :slots              {"monday"  [{:main-label   "Intro to Comp Sci 1"
                                    :abbreviation "CSCA08"
                                    :group        "CSCA08"
                                    :column       "monday"
                                    :start-time   10
                                    :end-time     12
                                    :optional     {:type "lec"}}]

                        "tuesday" [{:conflict?  true
                                    :start-time 9
                                    :end-time   11
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
                                                  :optional     {:type "lec"}}]}]}
   })
