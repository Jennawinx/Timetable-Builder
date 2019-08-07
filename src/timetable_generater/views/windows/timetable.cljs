(ns timetable-generater.views.windows.timetable
  (:require
    [re-frame.core :as rf]
    [timetable-generater.subscriptions]
    [clojure.string :as s]
    [timetable-generater.utils :as utils])
  (:use [resources.mar10-2019-study :only (test-table)]))

;; TODO fix grid logic
;; grid indices starts at 1
;; TODO pull out start-time?

(def table-view-location [:table-views "default"])
(def group-colours [:themes "default" :groups])
(def cell-view [:cell-views "default"])
(def hr-divions 4)                                          ; slot accuracy TODO this should not be manual
(def template-ignore-keys [:optional :template])

(defn t24->t12 [time]
  (let [x (mod time 12)]
    (if (= x 0) 12 x)))

(defn get-rownum [hr]
  (let [start-time @(rf/subscribe [:db-get-in (conj table-view-location :min-time)])]
    (-> hr
        (- start-time)                                      ;; calculate the time interval
        (* hr-divions)                                      ;; the division accuracy
        (inc)                                               ;; finish time is inclusive
        (inc)))                                             ;; B/C Grid counts at 1
  )

(defn get-colnum [colnum]
  (inc colnum)                                              ;; B/C Grid counts at 1
  )


(defn style-grid-block [column-start row-start row-end]
  {:grid-column-start (get-colnum column-start)
   :grid-column-end   (get-colnum (inc column-start))
   :grid-row-start    (get-rownum row-start)
   :grid-row-end      (get-rownum row-end)})


(defn table-headers [headers]
  (for [header (into [""] headers)]
    ^{:key header}
    [:div.day header]))


(defn time-labels [start-time end-time increment]
  (doall
    (for [time (range start-time (inc end-time) increment)]
      ^{:key time}
      [:div.time {:style (style-grid-block 0 time (+ time increment))}
       (str (t24->t12 time))])))


(defn time-slot-cell [parent-start parent-end {:keys [start-time end-time optional group] :as slot}]
  (let [data (merge (apply (partial dissoc slot) template-ignore-keys)
                    optional)]
    (println (utils/fill-template @(rf/subscribe [:db-get-in cell-view]) data))
    [:div.slot.info {:style {:background-color @(rf/subscribe [:db-get-in (conj group-colours group)])
                             :width            "100%"
                             :height           (-> (/ (- end-time start-time)
                                                      (- parent-end parent-start))
                                                   (* 100)
                                                   (int)
                                                   (str "%"))}}
     (utils/fill-template @(rf/subscribe [:db-get-in cell-view]) data)]))


(defn show-time-block [day-col slot]
  (let [{:keys [start-time end-time optional]} slot]
    [:div {:style (style-grid-block day-col start-time end-time)}
     [time-slot-cell start-time end-time slot]]))


(defn time-cell-summary [{:keys [start-time end-time abbreviation group]}]
  [:div.slot.info {:style {:background-color @(rf/subscribe [:db-get-in (conj group-colours group)])
                           :width            "100%"
                           :font-size        "1em"}}
   [:div {:style {:display         :flex
                  :justify-content :space-between}}
    [:div abbreviation] [:div start-time "-" end-time]]])


(defn show-conflicts [day-col slot]
  (let [{:keys [start-time end-time items]} slot
        colour "red"]
    [:div {:style (style-grid-block day-col start-time end-time)}
     (if (= (count items) 2)
       [:div {:style {:display         :flex
                      :justify-content :flex-start
                      :height          "100%"}}
        [time-slot-cell start-time end-time (first items)]
        [time-slot-cell start-time end-time (second items)]]
       (conj
         [:div.slot.info {:style {:background-color "rgba(255, 80, 80, 0.8)"
                                  :width            "100%"
                                  :height           "100%"}}
          [:div {:style {:margin-bottom "0.5em"}}
           "Conflict"]]
         (map time-cell-summary items)))]))


(defn entries []
  (doall
    (for [[day slots] @(rf/subscribe [:db-get-field :slots])
          {:keys [conflict?] :as slot} slots]
      (let [day-col (-> @(rf/subscribe [:db-get-in (conj table-view-location :display-days)])
                        (.indexOf day)
                        (get-colnum))]
        (if conflict?
          ^{:key slot}
          [show-conflicts day-col slot]
          ^{:key slot}
          [show-time-block day-col slot])))))


(defn load [& [config]]
  [:div#table-view config
   #_(println @(rf/subscribe [:db-get-in table-view-location]))
   (if-let [table-config @(rf/subscribe [:db-get-in table-view-location])]
     (let [{:keys [display-days width height increment min-time max-time]} table-config]
       [:div.table
        {:style {:display               :grid
                 :grid-template-columns (str "5ch repeat(" (count display-days) ", 1fr")
                 :grid-template-rows    (str "2em repeat(" (get-rownum max-time) ", 1fr")
                 :width                 width
                 :height                height
                 :margin                :inherit}}
        (table-headers display-days)
        (time-labels min-time max-time increment)
        (entries)])
     [:div])])