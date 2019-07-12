(ns timetable-generater.views.windows.add-slot
  (:require [fulcrologic.semantic-ui.factories :as f]
            [fulcrologic.semantic-ui.icons :as icons]
            [timetable-generater.custom-components :as custom]))

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


(defn load-optional-fields []
  [custom/field {:class "ui action input"}
   "Optional Field Example"
   (f/ui-input {})
   (f/ui-button {:icon true
                 :onClick #(println "delete me")}
                (f/ui-icon {:name icons/trash-icon}))])


(defn load []
  ;; NOTE Some state issues, better to just use pure reagent
  ;; NOTE onClick should not cause to reload
  [:div#add-slot
   [:div.ui.segment
    [:form {:class "ui unstackable form"}
     [:div.fields {:class "two"}
      [custom/field {:class "ten wide"} "Main Label" (f/ui-input {:onClick #(println "hi")})]
      [custom/field {:class "six wide"} "Abbreviation" (f/ui-input)]]
     [:div.fields {:class "three"}
      [custom/field {:class "five wide"} "Start Time" (f/ui-input)]
      [custom/field {:class "five wide"} "End Time" (f/ui-input)]
      [custom/field {:class "six wide"} "Group" (f/ui-input)]]
     (f/ui-divider)
     [load-optional-fields]
     [custom/field {:class "ui action input"}
      nil
      (f/ui-input {:placeholder "Add Label"}) (f/ui-button {:className "ui icon"
                                 :onClick #(println "Addd!!")}
                   (f/ui-icon {:name icons/add-icon}))]]]])
