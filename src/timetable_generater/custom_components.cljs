(ns timetable-generater.custom-components
  (:require [fulcrologic.semantic-ui.factories :as f]
            [fulcrologic.semantic-ui.icons :as i]
            [reagent.core :as reagent]
            [clojure.string :as s]))

(def typeahead-value (reagent/atom nil))

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

(defn ui-button
  [config content]
  [:div.ui.button config content])

(defn ui-input
  [config]
  [:div.ui.input
   [:input (assoc config :type :text)]])

;; ------- ui search -------

(defn match-search [strs]
  (fn [text callback]
    (->> strs
         (sort)
         (filter #(s/includes? (s/lower-case %) (s/lower-case text)))
         (take 5)
         (clj->js)
         (callback))))

(defn typeahead-mounted [options this]
  (.typeahead (js/$ (reagent/dom-node this))
              (clj->js {:hint      true
                        :highlight true
                        :minLength 1})
              (clj->js {:name   "states"
                        :source (match-search options)})))

(defn render-typeahead [config]
  [:input.typeahead.ui.search.dropdown
   (merge
     config
     {:type      :text
      :on-select #(reset! typeahead-value (-> % .-target .-value))})])

(defn typeahead [config options]
  (reagent/create-class
    {:component-did-mount (partial typeahead-mounted options)
     :reagent-render      (partial render-typeahead config)}))

(defn ui-search' [config options]
  ;(println options @typeahead-value)
  [:div.ui-widget
   [typeahead config options]])

;; Have to do this because of odd reference bugs
(defn ui-search [config options]
  [(partial ui-search' config options)])