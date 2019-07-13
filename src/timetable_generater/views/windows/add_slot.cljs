(ns timetable-generater.views.windows.add-slot
  (:require [fulcrologic.semantic-ui.factories :as f]
            [fulcrologic.semantic-ui.icons :as icons]
            [timetable-generater.custom-components :as custom]
            [re-frame.core :as rf]
            [timetable-generater.subscriptions]
            [timetable-generater.utils :refer [element-value]]
            [reagent.core :as r]))

(defn derive-key [label]
  ;; lowercase, no space, no colons
  )


(defn examples []
  (f/ui-button {:content       "Like"
                :icon          icons/heart-icon             ; or just "heart"
                :label         {:as "a" :basic true :content "2,048"}
                :labelPosition "right"})
  (f/ui-button-group nil
                     (f/ui-button {:icon true}
                                  (f/ui-icon {:name icons/align-left-icon}))
                     (f/ui-button {:icon true}
                                  (f/ui-icon {:name icons/align-center-icon}))
                     (f/ui-button {:icon true}
                                  (f/ui-icon {:name icons/align-right-icon}))
                     (f/ui-button {:icon true}
                                  (f/ui-icon {:name icons/align-justify-icon}))))

(defn load-optional-field []
  [:div.fields {:class "two"}
   [custom/field {:class "five wide"}
    "Optional Field Example" nil]
   [custom/field {:class "ui action input ten wide"}
    nil
    [custom/ui-input {} nil]
    [custom/ui-button
     {:class   "ui icon button-icon"
      :onClick #(println "delete me")}
     (f/ui-icon {:name icons/trash-icon})]]])

(defn load-optional-fields [fields]
  )



(defn load []
  [:div#add-slot
   [:div.ui.segment
    [:form {:class "ui unstackable form"}

     ;; first row
     [:div.fields {:class "two"}
      [custom/field {:class "ten wide"} "Main Label"
       [custom/ui-input {:onBlur #(rf/dispatch [:add-slot/update-field :main-label (element-value %)])}]]
      [custom/field {:class "six wide"} "Abbreviation"
       [custom/ui-input {:onBlur #(rf/dispatch [:add-slot/update-field :abbreviation (element-value %)])}]]]

     ;; second row
     [:div.fields {:class "three"}
      [custom/field {:class "five wide"} "Start Time"
       [custom/ui-input {:onBlur #(rf/dispatch [:add-slot/update-field :start-time (element-value %)])}]]
      [custom/field {:class "five wide"} "End Time"
       [custom/ui-input {:onBlur #(rf/dispatch [:add-slot/update-field :end-time (element-value %)])}]]
      [custom/field {:class "six wide"} "Group"
       [custom/ui-search
        {:onBlur #(rf/dispatch [:add-slot/update-field :group (element-value %)])}
        #{"asdf" "few"}]]]

     (f/ui-divider)

     ;; Add fields
     [load-optional-fields]

     (f/ui-divider)

     [custom/field {:class "ui action input"}
      nil
      [custom/ui-input {:placeholder "Add Label"}]
      [custom/ui-button
       {:class   "ui icon button-icon"
        :onClick #(println "Addd!!")}
       (f/ui-icon {:name icons/add-icon})]]

     ;; Finish
     [custom/ui-button
      {:class   "ui fluid button"
       :onClick #(cljs.pprint/pprint @(rf/subscribe [:db-peek]))}
      "Add Time Slot"]]]])