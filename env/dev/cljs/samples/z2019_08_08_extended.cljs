(ns samples.z2019-08-08-extended)

(def table-data
  {

   ;; ----------------------------------------- Replacable Here ---------------------------------------

   ;; Auto-fill
   :main-labels        {"Introduction to Numerical Algorithms for Computational Mathematics" "CSCC37"
                        "An Introduction to Probability"                                     "STAB52"
                        "Symbolic Logic I"                                                   "PHLB50"
                        "Listening to Music"                                                 "VPMA93"
                        "Introduction to Databases"                                          "CSCC43"
                        "Computer and Network Security"                                      "CSCD27"}

   :groups             #{"CSCC37" "STAB52" "PHLB50" "VPMA93" "CSCC43" "CSCD27"
                         "Commute" "Sleep" "Eat" "Bathroom"}
   :columns            #{"mon" "tue" "wed" "thu" "fri" "sat" "sun"}
   :labels             #{"room" "type" "number"}

   ;; styling
   :themes             {"default" {:groups {"CSCC37"   "rgb(164, 217, 217)"
                                            "STAB52"   "rgb(255, 198, 200)"
                                            "PHLB50"   "rgb(177, 207, 242)"
                                            "VPMA93"   "rgb(217, 192, 255)"
                                            "CSCC43"   "rgb(200, 227, 182)"
                                            "CSCD27"   "rgb(242, 179, 253)"
                                            "Commute"  "burlywood"
                                            "Sleep"    "indianred"
                                            "Eat"      "cadetblue"
                                            "Bathroom" "gray"}}}

   :cell-views         {"default"  [:div.slot.info {:style {:padding "0.20em"}}
                                    [:div {:style {:text-transform  :uppercase
                                                   :font-size       "80%"
                                                   :display         :flex
                                                   :justify-content :space-between}}
                                     [:div "{%type%} {%number%}"] [:div "{%room%}"]]
                                    [:div {:style {:font-weight    :bold
                                                   :text-transform :uppercase
                                                   :font-size      "95%"
                                                   :margin-top     "0.20em"}}
                                     "{%abbreviation%}"]]
                        "Eat"      [:div.slot.info {:style {:display     :flex
                                                            :align-items :center}}
                                    [:div {:style {:font-weight   :bold
                                                   :font-size     "100%"
                                                   :margin-bottom "0.25em"
                                                   :width         "100%"
                                                   }}
                                     "{%abbreviation%}"]]
                        "Sleep"    [:div.slot.info {:style {:display     :flex
                                                            :align-items :center}}
                                    [:div {:style {:font-weight   :bold
                                                   :font-size     "100%"
                                                   :margin-bottom "0.25em"
                                                   :width         "100%"
                                                   }}
                                     "{%abbreviation%}"]]
                        "Commute"  [:div.slot.info {:style {:display     :flex
                                                            :align-items :center}}
                                    [:div {:style {:margin :auto}}
                                     [:div {:style {:font-weight   :bold
                                                    :font-size     "100%"
                                                    :margin-bottom "0.25em"
                                                    :width         "100%"}}
                                      "{%abbreviation%}"]
                                     [:div {:style {:font-size   "75%"
                                                    :line-height 1}}
                                      "Doodle · Review" [:br]
                                      "Think"]]
                                    ]
                        "Bathroom" [:div.slot.info {:style {:display     :flex
                                                            :align-items :center}}
                                    [:div {:style {:font-weight   :bold
                                                   :font-size     "80%"
                                                   :margin-bottom "0.25em"
                                                   :width         "100%"
                                                   }}
                                     "{%abbreviation%}"]]
                        "ghost"    [:div.slot.info {:style {:color            "snow"
                                                            :background-color "rgb(60, 65, 60)"
                                                            :border           "2px dashed snow"
                                                            :padding          "0.20em"}}
                                    [:div {:style {:text-transform  :uppercase
                                                   :font-size       "80%"
                                                   :display         :flex
                                                   :justify-content :space-between}}
                                     [:div "{%type%} {%number%}"] [:div "{%room%}"]]
                                    [:div {:style {:font-weight    :bold
                                                   :text-transform :uppercase
                                                   :font-size      "95%"
                                                   :margin-top     "0.20em"}}
                                     "{%abbreviation%}"]]}

   :table-views        {"default" {:display-days ["mon" "tue" "wed" "thu" "fri"]
                                   :width        "74vh"
                                   :height       "100vh"
                                   :increment    1
                                   :min-time     7
                                   :max-time     23}}

   ;; data
   :slots              {"mon"
                        [{:group        "Bathroom",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Bathroom",
                          :abbreviation "Bathroom",
                          :column       "mon",
                          :template     "Bathroom",
                          :end-time     7.25,
                          :start-time   7}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "mon",
                          :template     "Eat",
                          :end-time     7.75,
                          :start-time   7.25}
                         {:group        "Commute",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Commute",
                          :abbreviation "Commute",
                          :column       "mon",
                          :template     "Commute",
                          :end-time     9,
                          :start-time   7.75}
                         {:group        "VPMA93",
                          :optional     {"number" "1", "type" "lec", "room" "AA 112"},
                          :main-label   "Listening to Music",
                          :column       "mon",
                          :abbreviation "VPMA93",
                          :start-time   9,
                          :end-time     11}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "mon",
                          :template     "Eat",
                          :end-time     11.5,
                          :start-time   11}
                         {:group        "CSCD27",
                          :optional     {"number" "4", "type" "tut", "room" "IC 302"},
                          :main-label   "Computer and Network Security",
                          :abbreviation "CSCD27",
                          :column       "mon",
                          :start-time   12,
                          :end-time     13}
                         {:group        "CSCD27",
                          :optional     {"number" "1", "type" "pra", "room" "BV 473"},
                          :main-label   "Computer and Network Security",
                          :abbreviation "CSCD27",
                          :column       "mon",
                          :start-time   13,
                          :end-time     14}
                         {:group        "CSCC37",
                          :optional     {"number" "1", "type" "lec", "room" "AA 112"},
                          :main-label
                                        "Introduction to Numerical Algorithms for Computational Mathematics",
                          :abbreviation "CSCC37",
                          :column       "mon",
                          :start-time   14,
                          :end-time     17}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "mon",
                          :template     "Eat",
                          :end-time     17.5,
                          :start-time   17}
                         {:group        "CSCC37",
                          :optional     {"number" "10", "type" "tut", "room" "IC 326"},
                          :main-label
                                        "Introduction to Numerical Algorithms for Computational Mathematics",
                          :abbreviation "CSCC37",
                          :column       "mon",
                          :start-time   18,
                          :end-time     19}
                         {:group        "Commute",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Commute",
                          :abbreviation "Commute",
                          :column       "mon",
                          :template     "Commute",
                          :end-time     22,
                          :start-time   20.75}
                         {:group        "Bathroom",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Bathroom",
                          :abbreviation "Bathroom",
                          :column       "mon",
                          :template     "Bathroom",
                          :end-time     22.5,
                          :start-time   22}],
                        "thu"
                        [{:group        "Bathroom",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Bathroom",
                          :abbreviation "Bathroom",
                          :column       "thu",
                          :template     "Bathroom",
                          :end-time     7.25,
                          :start-time   7}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "thu",
                          :template     "Eat",
                          :end-time     7.75,
                          :start-time   7.25}
                         {:group        "Commute",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Commute",
                          :abbreviation "Commute",
                          :column       "thu",
                          :template     "Commute",
                          :end-time     9,
                          :start-time   7.75}
                         {:group        "STAB52",
                          :optional     {"number" "1", "type" "lec", "room" "SW 309"},
                          :main-label   "An Introduction to Probability",
                          :abbreviation "STAB52",
                          :column       "thu",
                          :start-time   9,
                          :end-time     11}
                         {:group        "CSCD27",
                          :optional     {"number" "1", "type" "lec", "room" "SW 143"},
                          :main-label   "Computer and Network Security",
                          :abbreviation "CSCD27",
                          :column       "thu",
                          :start-time   11,
                          :end-time     13}
                         {:group        "PHLB50",
                          :optional     {"number" "1", "type" "lec", "room" "HW 215"},
                          :main-label   "Symbolic Logic I",
                          :abbreviation "PHLB50",
                          :column       "thu",
                          :start-time   13,
                          :end-time     14}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "thu",
                          :template     "Eat",
                          :end-time     17.5,
                          :start-time   17}
                         {:group        "CSCC43",
                          :optional
                                        {"number"   "1",
                                         "type"     "lec",
                                         "room"     "SW 143",
                                         "template" "alternate"},
                          :main-label   "Introduction to Databases",
                          :abbreviation "CSCC43",
                          :column       "thu",
                          :start-time   18,
                          :end-time     20,
                          :template     "ghost"}
                         {:group        "Commute",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Commute",
                          :abbreviation "Commute",
                          :column       "thu",
                          :template     "Commute",
                          :end-time     21.25,
                          :start-time   20}
                         {:group        "Bathroom",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Bathroom",
                          :abbreviation "Bathroom",
                          :column       "thu",
                          :template     "Bathroom",
                          :end-time     21.75,
                          :start-time   21.25}],
                        "tue"
                        [{:group        "Bathroom",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Bathroom",
                          :abbreviation "Bathroom",
                          :column       "tue",
                          :template     "Bathroom",
                          :end-time     7.25,
                          :start-time   7}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "tue",
                          :template     "Eat",
                          :end-time     7.75,
                          :start-time   7.25}
                         {:group        "Commute",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Commute",
                          :abbreviation "Commute",
                          :column       "tue",
                          :template     "Commute",
                          :end-time     9,
                          :start-time   7.75}
                         {:group        "STAB52",
                          :optional     {"number" "1", "type" "lec", "room" "SW 319"},
                          :main-label   "An Introduction to Probability",
                          :abbreviation "STAB52",
                          :column       "tue",
                          :start-time   9,
                          :end-time     11}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "tue",
                          :template     "Eat",
                          :end-time     11.5,
                          :start-time   11}
                         {:group        "CSCC43",
                          :optional     {"number" "1", "type" "tut", "room" "IC 308"},
                          :main-label   "Introduction to Databases",
                          :abbreviation "CSCC43",
                          :column       "tue",
                          :start-time   12,
                          :end-time     13,
                          :template     "ghost"}
                         {:group        "PHLB50",
                          :optional     {"number" "1", "type" "lec", "room" "HW 215"},
                          :main-label   "Symbolic Logic I",
                          :column       "tue",
                          :start-time   13,
                          :end-time     15,
                          :abbreviation "PHLB50"}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "tue",
                          :template     "Eat",
                          :end-time     17.5,
                          :start-time   17}
                         {:group        "Commute",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Commute",
                          :abbreviation "Commute",
                          :column       "tue",
                          :template     "Commute",
                          :end-time     21,
                          :start-time   19.75}
                         {:group        "Bathroom",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Bathroom",
                          :abbreviation "Bathroom",
                          :column       "tue",
                          :template     "Bathroom",
                          :end-time     21.5,
                          :start-time   21}],
                        "fri"
                        [{:group        "Sleep",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Sleep",
                          :abbreviation "Sleep",
                          :column       "fri",
                          :end-time     7.75,
                          :start-time   7,
                          :template     "Sleep"}
                         {:group        "Bathroom",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Bathroom",
                          :abbreviation "Bathroom",
                          :column       "fri",
                          :template     "Bathroom",
                          :end-time     8,
                          :start-time   7.75}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "fri",
                          :template     "Eat",
                          :end-time     8.5,
                          :start-time   8}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "fri",
                          :template     "Eat",
                          :end-time     11.5,
                          :start-time   11}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "fri",
                          :template     "Eat",
                          :end-time     17.5,
                          :start-time   17}],
                        "wed"
                        [{:group        "Sleep",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Sleep",
                          :abbreviation "Sleep",
                          :column       "wed",
                          :end-time     7.75,
                          :start-time   7,
                          :template     "Sleep"}
                         {:group        "Bathroom",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Bathroom",
                          :abbreviation "Bathroom",
                          :column       "wed",
                          :template     "Bathroom",
                          :end-time     8,
                          :start-time   7.75}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "wed",
                          :template     "Eat",
                          :end-time     8.5,
                          :start-time   8}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "wed",
                          :template     "Eat",
                          :end-time     11.5,
                          :start-time   11}
                         {:group        "Eat",
                          :optional     {"number" "", "type" "", "room" ""},
                          :main-label   "Eat",
                          :abbreviation "Eat",
                          :column       "wed",
                          :template     "Eat",
                          :end-time     17.5,
                          :start-time   17}]}

   ;; ----------------------------------------- Replacable Here ---------------------------------------

   ;; active
   :current-theme      "default"
   :current-table-view "default"

   ;; editor temp & rendering
   :editor             {:add-slot {:data         {:group    ""
                                                  :optional {"number" ""
                                                             "type"   ""
                                                             "room"   ""}}
                                   :field-to-add ""}}
   })
