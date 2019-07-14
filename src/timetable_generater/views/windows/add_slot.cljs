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
(def rf-sub-location-required (conj rf-sub-location :required))
(def rf-sub-location-optional (conj rf-sub-location :optional))


(defn load-optional-field [field]
  [:div.fields {:class "two"}
   [custom/field {:class "four wide"} field]
   [custom/field {:class "ui action input sixteen wide"} nil
    #_[custom/ui-db-input (conj rf-sub-location :optional field)]
    [custom/ui-input
     {:defaultValue @(rf/subscribe [:add-slot/get-optional-field field])
      :onBlur       #(rf/dispatch [:add-slot/update-optional-field field (element-value %)])}]
    [custom/ui-button
     {:class   "ui icon button-icon"
      :onClick #(println "delete me")}
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
      {:value    (or @(rf/subscribe [:add-slot/get-required-field :abbreviation])
                     @(rf/subscribe [:add-slot/get-abbreviation])
                     @(rf/subscribe [:add-slot/get-required-field :main-label]))
       :onChange #(rf/dispatch [:add-slot/update-required-field :abbreviation (element-value %)])}]]
    [custom/field {:class "five wide"} "Group"
     [custom/ui-db-search (conj rf-sub-location-required :group) @(rf/subscribe [:db-get-field :groups])]]]

   ;; second row
   [:div.fields {:class "three"}
    [custom/field {:class "six wide"} "Day/Section"
     [custom/ui-db-input (conj rf-sub-location-required :end-time)]]
    [custom/field {:class "five wide"} "Start Time"
     [custom/ui-db-input (conj rf-sub-location-required :start-time)]]
    [custom/field {:class "five wide"} "End Time"
     [custom/ui-db-input (conj rf-sub-location-required :end-time)]]]])

(defn add-field []
  ;; TODO field name validation
  [custom/field {:class "ui action input"}
   nil
   [custom/ui-input
    {:placeholder "Add Field"
     :onBlur      #(rf/dispatch [:add-slot/update-field-to-add (element-value %)])}]
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
     [load-optional-fields (keys @(rf/subscribe [:add-slot/get-optional-fields]))]
     (f/ui-divider)
     [add-field]

     ;; Finish
     [:div.fields {:class "two"}
      [custom/ui-button
       {:class   "ui button field twelve wide"
        :onClick #(cljs.pprint/pprint @(rf/subscribe [:db-peek]))}
       "Add Time Slot"]
      [custom/ui-button
       {:class   "ui button field four wide"
        :onClick #(rf/dispatch [:db-assoc-in rf-sub-location {}])}
       "Clear All"]]

     ]]])