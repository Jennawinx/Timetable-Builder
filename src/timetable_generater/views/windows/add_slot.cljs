(ns timetable-generater.views.windows.add-slot
  (:require [fulcrologic.semantic-ui.factories :as f]
            [fulcrologic.semantic-ui.icons :as icons]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [timetable-generater.subscriptions :as subs]
            [timetable-generater.custom-components :as custom]
            [timetable-generater.utils :refer [element-value]]))


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
       (into [:div {:style {:height "30vh"
                            :overflow-y :scroll
                            :overflow-x :hidden}}])))


(defn load-required-fields []
  [:div

   ;; first row

   [:div.fields {:class "three"}
    [custom/field {:class "eight wide"} "Main Label"
     [custom/ui-db-search (conj rf-sub-location-required :main-label) (keys @(rf/subscribe [:db-get-field :main-labels]))]]
    [custom/field {:class "three wide"} "Abbreviation"
     [custom/ui-db-input (conj rf-sub-location-required :abbreviation)
      ;; TODO do not use old subs
      {:value (or @(rf/subscribe [:db-get-in (conj rf-sub-location-required :abbreviation)])
                  @(rf/subscribe [:add-slot/get-abbreviation])
                  @(rf/subscribe [:db-get-in (conj rf-sub-location-required :main-label)]))}]]
    [custom/field {:class "five wide"} "Group"
     [custom/ui-db-search (conj rf-sub-location-required :group) @(rf/subscribe [:db-get-field :groups])]]]

   ;; second row

   (let [start-time @(rf/subscribe [:db-get-in (conj rf-sub-location-required :start-time)])
         end-time   @(rf/subscribe [:db-get-in (conj rf-sub-location-required :end-time)])
         min-time   @(rf/subscribe [:db-get-in (conj subs/table-view-location :min-time)])
         max-time   @(rf/subscribe [:db-get-in (conj subs/table-view-location :max-time)])]

     [:div.fields {:class "three"}
      [custom/field {:class "six wide"} "Day/Section"
       [custom/ui-db-search (conj rf-sub-location-required :column) @(rf/subscribe [:db-get-field :columns])]]

      ;; TODO FACTOR OUT TIME STUFF

      [custom/field {:class "three wide"} "Start Time"
       [custom/ui-db-input (conj rf-sub-location-required :start-time)
        {:type     "number"
         ;; TODO how to fix do they don't break on hot reload
         ;; for the side increments
         #_#_:onChange #(let [start-time (custom/cast :number (element-value %))]
                      (when (number? start-time)
                        (rf/dispatch [:db-assoc-in (conj rf-sub-location-required :start-time)
                                      (if (<= end-time start-time)
                                        (do (rf/dispatch [:db-assoc-in (conj rf-sub-location-required :end-time) (+ start-time 1)])
                                            start-time)
                                        start-time)])))
         ;; for typing in input
         #_#_:onBlur   #(let [start-time (custom/cast :number (element-value %))]
                      (when (number? start-time)
                        (rf/dispatch [:db-assoc-in (conj rf-sub-location-required :start-time)
                                      (cond (or (< start-time min-time) (> (element-value %) max-time))
                                            min-time
                                            (<= end-time start-time)
                                            (do (rf/dispatch [:db-assoc-in (conj rf-sub-location-required :end-time) (+ start-time 1)])
                                                start-time)

                                            :else start-time)])))
         :pattern  "\\d+"
         :min      min-time
         :max      max-time
         :step     1
         :cast     :number}]]

      [custom/field {:class "three wide"} "End Time"
       [custom/ui-db-input (conj rf-sub-location-required :end-time)
        {:type    "number"
         ;; for the side increments
         ;; NOTE BUGGY because when backing and typing, single letter maybe less than start-time
         ;:onChange #(let [end-time (custom/cast :number (element-value %))]
         ;             (when (some? end-time)
         ;               (rf/dispatch [:db-assoc-in (conj rf-sub-location-required :end-time)
         ;                             (if (<= end-time start-time)
         ;                               (do (rf/dispatch [:db-assoc-in (conj rf-sub-location-required :start-time) (- end-time 1)])
         ;                                   end-time)
         ;                               end-time)])))

         ;; TODO how to fix do they don't break on hot reload
         ;; for typing in input
         #_#_:onBlur  #(let [end-time (custom/cast :number (element-value %))]
                     (when (some? end-time)
                       (rf/dispatch [:db-assoc-in (conj rf-sub-location-required :end-time)
                                     (cond (or (< end-time min-time) (> (element-value %) max-time))
                                           max-time

                                           (<= end-time start-time)
                                           (do (rf/dispatch [:db-assoc-in (conj rf-sub-location-required :start-time) (- end-time 1)])
                                               end-time)

                                           :else end-time)])))

         :pattern "\\d*"
         :min     min-time
         :max     max-time
         :step    1
         :cast    :number}]]

      [custom/field {:class "four wide"} "Template"
       [custom/ui-db-search (conj rf-sub-location-required :template) (keys @(rf/subscribe [:db-get-field :cell-views])) {:placeholder "default"}]]])])


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
       {:class   "ui button field ten teal wide"
        :onClick #(rf/dispatch [:add-slot/add-slot])}
       "Add Time Slot"]
      [custom/ui-button
       {:class   "ui button field six red wide"
        :onClick #(rf/dispatch [:db-assoc-in rf-sub-location {}])}
       "Clear All"]]]]])