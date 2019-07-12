(ns timetable-generater.custom-components
  (:require [fulcrologic.semantic-ui.factories :as f]
            [fulcrologic.semantic-ui.icons :as i]))

(defn field
  ([label input]
   (field {} label input))
  ([{:keys [class] :as options} label input]
   [:div.field {:class class}
    [:label label]
    input])
  ([{:keys [class] :as options} label input & args]
   (apply conj
          (field options label input)
          args)))