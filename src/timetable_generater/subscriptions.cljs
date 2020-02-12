(ns timetable-generater.subscriptions
  (:require [re-frame.core :as rf]
            [timetable-generater.time-block-logic :as time-logic])
  (:use [samples.z2019-08-08 :only (table-data)]))


(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    (or table-data
        {
         ;; ----------------------------------------- Replacable Here ---------------------------------------

         ;; Auto-fill
         :main-labels        {}
         :groups             #{}
         :columns            #{"mon" "tue" "wed" "thu" "fri" "sat" "sun"}
         :labels             #{"room" "type" "number"}

         ;; styling
         :themes             {"default" {:groups {}}}

         :cell-views         {"default"   [:div.slot.info {:style {:color "black"}}
                                           [:div {:style {:font-weight    :bold
                                                          :text-transform :uppercase
                                                          :font-size      "110%"
                                                          :margin-bottom  "0.25em"
                                                          }}
                                            "{%abbreviation%}"]
                                           [:div {:style {:text-transform :uppercase}}
                                            "{%type%} {%number%}" [:br]
                                            "{%room%}"]]
                              "ghost"     [:div.slot.info {:style {:color            "snow"
                                                                   :background-color "rgb(60, 65, 60)"
                                                                   :border           "2px dashed snow"}}
                                           [:div {:style {:font-weight    :bold
                                                          :text-transform :uppercase
                                                          :font-size      "110%"
                                                          :margin-bottom  "0.25em"}}
                                            "{%abbreviation%}"]
                                           [:div {:style {:text-transform :uppercase}}
                                            "{%type%} {%number%}" [:br]
                                            "{%room%}"]]}

         :table-views        {"default" {:display-days ["mon" "tue" "wed" "thu" "fri"]
                                         :width        "74vh"
                                         :height       "100vh"
                                         :increment    1
                                         :min-time     9
                                         :max-time     19}}

         ;; data
         :slots              {}

         ;; ----------------------------------------- Replacable Here ---------------------------------------

         ;; active
         :current-theme      "default"
         :current-table-view "default"

         ;; editor
         :editor             {:add-slot {:data         {:optional {}}
                                         :field-to-add ""}}})))


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
