(ns timetable-generater.custom-components
  (:require [clojure.string :as s]
            [reagent.core :as reagent]

            [fulcrologic.semantic-ui.factories :as f]
            [fulcrologic.semantic-ui.icons :as i]

            [re-frame.core :as rf]
            [timetable-generater.subscriptions]
            [timetable-generater.utils :refer [element-value]]))

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

(defn ui-db-input
  [data-location config]
  [ui-input
   (merge
     {:value    @(rf/subscribe [:db-get-in data-location])
      :onChange #(rf/dispatch [:db-assoc-in data-location (element-value %)])}
     config)])

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

(defn ui-db-search
  [data-location options config]
  [ui-search
   (merge
     {:defaultValue @(rf/subscribe [:db-get-in data-location])
      :onBlur       #(rf/dispatch [:db-assoc-in data-location (element-value %)])}
     config)
   options])