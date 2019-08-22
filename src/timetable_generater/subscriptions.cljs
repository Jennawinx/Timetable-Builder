(ns timetable-generater.subscriptions
  (:require [re-frame.core :as rf]
            [timetable-generater.time-block-logic :as time-logic]))

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    {

     ;; ----------------------------------------- Replacable Here ---------------------------------------

     ;; Auto-fill
     :main-labels        {"Introduction to Numerical Algorithms for Computational Mathematics" "CSCC37"
                          "An Introduction to Probability"                                     "STAB52"
                          "Symbolic Logic I"                                                   "PHLB50"
                          "Listening to Music"                                                 "VPMA93"
                          "Introduction to Databases"                                          "CSCC43"
                          "Computer and Network Security"                                      "CSCD27"}

     :groups             #{"CSCC37" "STAB52" "PHLB50" "VPMA93" "CSCC43" "CSCD27"}
     :columns            #{"mon" "tue" "wed" "thu" "fri" "sat" "sun"}
     :labels             #{"room" "type" "number"}

     ;; styling
     :themes             {"default" {:groups {"CSCC37" "rgb(164, 217, 217)"
                                              "STAB52" "rgb(255, 198, 200)"
                                              "PHLB50" "rgb(177, 207, 242)"
                                              "VPMA93" "rgb(217, 192, 255)"
                                              "CSCC43" "rgb(200, 227, 182)"
                                              "CSCD27" "rgb(242, 179, 253)"}}}

     :cell-views         {"default" [:div.slot.info {:style {:padding "0.20em"}}
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
                          "Eat"     [:div.slot.info {:style {:display     :flex
                                                             :align-items :center}}
                                     [:div {:style {:font-weight   :bold
                                                    :font-size     "100%"
                                                    :margin-bottom "0.25em"
                                                    :width         "100%"
                                                    }}
                                      "{%abbreviation%}"]]
                          "Sleep"   [:div.slot.info {:style {:display     :flex
                                                             :align-items :center}}
                                     [:div {:style {:font-weight   :bold
                                                    :font-size     "100%"
                                                    :margin-bottom "0.25em"
                                                    :width         "100%"
                                                    }}
                                      "{%abbreviation%}"]]
                          "Commute" [:div.slot.info {:style {:display     :flex
                                                             :align-items :center}}
                                     [:div {:style {:font-weight   :bold
                                                    :font-size     "100%"
                                                    :margin-bottom "0.25em"
                                                    :width         "100%"
                                                    }}
                                      "{%abbreviation%}"]]
                          "ghost"   [:div.slot.info {:style {:color            "snow"
                                                             :background-color "black"
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
                                     :min-time     8
                                     :max-time     23}}

     ;; data
     :slots              {"mon"
                          [{:group        "Commute",
                            :optional     {"number" "", "type" "", "room" ""},
                            :main-label   "Commute",
                            :abbreviation "Commute",
                            :column       "mon",
                            :template     "Commute",
                            :end-time     9,
                            :start-time   8}
                           {:group        "VPMA93",
                            :optional     {"number" "1", "type" "lec", "room" "AA 112"},
                            :main-label   "Listening to Music",
                            :column       "mon",
                            :abbreviation "VPMA93",
                            :start-time   9,
                            :end-time     11}
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
                           {:group        "CSCC37",
                            :optional     {"number" "10", "type" "tut", "room" "IC 326"},
                            :main-label
                                          "Introduction to Numerical Algorithms for Computational Mathematics",
                            :abbreviation "CSCC37",
                            :column       "mon",
                            :start-time   18,
                            :end-time     19}
                           {:group        "Sleep",
                            :optional     {"number" "", "type" "", "room" ""},
                            :main-label   "Sleep",
                            :abbreviation "Sleep",
                            :column       "mon",
                            :end-time     23,
                            :start-time   22,
                            :template     "Sleep"}]
                          "thu"
                          [{:group        "Commute",
                            :optional     {"number" "", "type" "", "room" ""},
                            :main-label   "Commute",
                            :abbreviation "Commute",
                            :column       "thu",
                            :template     "Commute",
                            :end-time     9,
                            :start-time   8}
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
                           {:group        "Sleep",
                            :optional     {"number" "", "type" "", "room" ""},
                            :main-label   "Sleep",
                            :abbreviation "Sleep",
                            :column       "thu",
                            :end-time     23,
                            :start-time   22,
                            :template     "Sleep"}],
                          "tue"
                          [{:group        "Commute",
                            :optional     {"number" "", "type" "", "room" ""},
                            :main-label   "Commute",
                            :abbreviation "Commute",
                            :column       "tue",
                            :template     "Commute",
                            :end-time     9,
                            :start-time   8}
                           {:group        "STAB52",
                            :optional     {"number" "1", "type" "lec", "room" "SW 319"},
                            :main-label   "An Introduction to Probability",
                            :abbreviation "STAB52",
                            :column       "tue",
                            :start-time   9,
                            :end-time     11}
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
                           {:group        "Sleep",
                            :optional     {"number" "", "type" "", "room" ""},
                            :main-label   "Sleep",
                            :abbreviation "Sleep",
                            :column       "tue",
                            :end-time     23,
                            :start-time   22,
                            :template     "Sleep"}],
                          "fri"
                          [{:group        "Commute",
                            :optional     {"number" "", "type" "", "room" ""},
                            :main-label   "Commute",
                            :abbreviation "Commute",
                            :column       "fri",
                            :template     "Commute",
                            :end-time     9,
                            :start-time   8}
                           {:group        "CSCC43",
                            :optional
                                          {"number"   "1",
                                           "type"     "lec",
                                           "room"     "SW 143",
                                           "template" "alternate"},
                            :main-label   "Introduction to Databases",
                            :abbreviation "CSCC43",
                            :column       "fri",
                            :start-time   10,
                            :end-time     12,
                            :template     "ghost"}
                           {:group        "Sleep",
                            :optional     {"number" "", "type" "", "room" ""},
                            :main-label   "Sleep",
                            :abbreviation "Sleep",
                            :column       "fri",
                            :end-time     23,
                            :start-time   22,
                            :template     "Sleep"}],,
                          "wed"
                          [{:group        "Sleep",
                            :optional     {"number" "", "type" "", "room" ""},
                            :main-label   "Sleep",
                            :abbreviation "Sleep",
                            :column       "wed",
                            :end-time     23,
                            :start-time   22,
                            :template     "Sleep"}]}

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
     }))


(def table-view-location [:table-views "default"])
(def default-group-colours [:themes "default" :groups])
(def template-ignore-keys [:optional :template])


(rf/reg-sub
  :db-peek
  (fn [db _]
    db))

(rf/reg-sub
  :db-get-field
  (fn [db [_ field]]
    (get db field)))

(rf/reg-sub
  :db-get-in
  (fn [db [_ location]]
    (get-in db location)))

(rf/reg-event-db
  :db-assoc-in
  (fn [db [_ location value]]
    (assoc-in db location value)))

(rf/reg-event-db
  :db-update-in
  (fn [db [_ location function & values]]
    (apply update-in db location function values)))

;; ---------------- ADD SLOT ----------------

(rf/reg-sub
  :add-slot/get-field-to-add
  (fn [db [_ _]]
    (get-in db [:editor :add-slot :field-to-add])))

(rf/reg-event-db
  :add-slot/update-field-to-add
  (fn [db [_ field]]
    (assoc-in db [:editor :add-slot :field-to-add] field)))

(rf/reg-event-db
  :add-slot/add-optional-field
  (fn [db _]
    (let [field @(rf/subscribe [:add-slot/get-field-to-add])]
      ;; TODO do not have check here
      (if (= (clojure.string/trim field) "")
        db
        (assoc-in db [:editor :add-slot :data :optional field] "")))))

(rf/reg-sub
  :add-slot/get-abbreviation
  (fn [db _]
    (get-in db [:main-labels (get-in db [:editor :add-slot :data :main-label])])))

(rf/reg-event-db
  :add-slot/add-slot
  (fn [db _]
    (let [{:keys [main-label group label column abbreviation] :as slot} (get-in db [:editor :add-slot :data])]
      (-> db
          (update :slots time-logic/add-time-slot slot)
          (update :main-labels assoc main-label abbreviation)
          (update :groups conj group)
          (update :labels conj label)
          (update :columns conj column)))))
