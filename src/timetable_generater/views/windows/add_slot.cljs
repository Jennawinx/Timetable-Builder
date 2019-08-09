(ns timetable-generater.views.windows.add-slot
  (:require [fulcrologic.semantic-ui.factories :as f]
            [fulcrologic.semantic-ui.icons :as icons]
            [timetable-generater.custom-components :as custom]
            [re-frame.core :as rf]
            [timetable-generater.subscriptions]
            [timetable-generater.utils :refer [element-value]]
            [reagent.core :as r]))


(def editor :add-slot)
(def rf-sub-location [:editor editor])
(def rf-sub-location-data (conj rf-sub-location :data))
(def rf-sub-location-required rf-sub-location-data)
(def rf-sub-location-optional (conj rf-sub-location-data :optional))

;; TODO validate add-slot
;; TODO add valid slots to db

(defn load-optional-field [field]
  [:div.fields {:class "two"}
   [custom/field {:class "four wide"} field]
   [custom/field {:class "ui action input sixteen wide"} nil
    [custom/ui-db-input (conj rf-sub-location-optional field)]
    [custom/ui-button
     {:class   "ui icon button-icon"
      :onClick #(rf/dispatch [:db-update-in rf-sub-location-optional dissoc field])}
     (f/ui-icon {:name icons/trash-icon})]]])


(defn load-optional-fields [fields]
  (->> fields
       sort
       (map load-optional-field)
       (into [:div])))


(defn load-required-fields []
  [:div
   ;; first row
   [:div.fields {:class "three"}
    [custom/field {:class "eight wide"} "Main Label"
     [custom/ui-db-search (conj rf-sub-location-required :main-label) (keys @(rf/subscribe [:db-get-field :main-labels]))]]
    [custom/field {:class "three wide"} "Abbreviation"
     [custom/ui-db-input (conj rf-sub-location-required :abbreviation)
      ;; TODO do not use old subs
      {:value    (or @(rf/subscribe [:db-get-in (conj rf-sub-location-required :abbreviation)])
                     @(rf/subscribe [:add-slot/get-abbreviation])
                     @(rf/subscribe [:db-get-in (conj rf-sub-location-required :main-label)]))}]]
    [custom/field {:class "five wide"} "Group"
     [custom/ui-db-search (conj rf-sub-location-required :group) @(rf/subscribe [:db-get-field :groups])]]]

   ;; second row
   [:div.fields {:class "three"}
    [custom/field {:class "six wide"} "Day/Section"
     [custom/ui-db-search (conj rf-sub-location-required :column) @(rf/subscribe [:db-get-field :columns])]]
    [custom/field {:class "five wide"} "Start Time"
     [custom/ui-db-input (conj rf-sub-location-required :start-time) {:type "number" :min 0 :max 24 :step 1 :cast :number}]]
    [custom/field {:class "five wide"} "End Time"
     [custom/ui-db-input (conj rf-sub-location-required :end-time) {:type "number" :min 0 :max 24 :step 1 :cast :number}]]]])


(defn add-field []
  ;; TODO field name validation
  [custom/field {:class "ui action input"} ""
   [custom/ui-search
    {:placeholder "Add Field"
     :onBlur      #(rf/dispatch [:add-slot/update-field-to-add (element-value %)])}
    @(rf/subscribe [:db-get-field :labels])]
   [custom/ui-button
    {:class   "ui icon button-icon"
     :onClick #(rf/dispatch [:add-slot/add-optional-field])}
    (f/ui-icon {:name icons/add-icon})]])


(defn load []
  [:div#add-slot
   [:div.ui.segment
    [:form {:class "ui unstackable form"}
     [load-required-fields]
     (f/ui-divider)
     [load-optional-fields (keys @(rf/subscribe [:db-get-in rf-sub-location-optional]))]
     (f/ui-divider)
     [add-field]
     ;; Finish
     [:div.fields {:class "two"}
      [custom/ui-button
       {:class   "ui button field twelve wide"
        :onClick #(rf/dispatch [:add-slot/add-slot])}
       "Add Time Slot"]
      [custom/ui-button
       {:class   "ui button field four wide"
        :onClick #(rf/dispatch [:db-assoc-in rf-sub-location {}])}
       "Clear All"]
      [custom/ui-button
       {:class   "ui button field one wide"
        :onClick #(cljs.pprint/pprint @(rf/subscribe [:db-peek]))}
       "Peek"]]]]])