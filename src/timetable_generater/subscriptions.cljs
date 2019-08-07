(ns timetable-generater.subscriptions
  (:require [re-frame.core :as rf]
            [timetable-generater.time-block-logic :as time-logic]))

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
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
                                     [:div "{%abbreviation%}"]
                                     [:div "{%type%}"]]}

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
     :slots              #_{}
                         {"monday"  [{:main-label   "Intro to Comp Sci 1"
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

     ;; editor temp & rendering
     :editor             {:add-slot {:data         {:group    "hi"
                                                    :optional {"bugs" "hi"
                                                               "fish" "bye"}}
                                     :field-to-add ""}}
     }))

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
    (update db :slots time-logic/add-time-slot (get-in db [:editor :add-slot :data]))))
