(ns timetable-generater.views.windows.timetable
  (:require
    [clojure.string :as s]
    [re-frame.core :as rf]
    [timetable-generater.subscriptions :as subs]
    [timetable-generater.utils :as utils :refer [vector-remove]]
    [timetable-generater.views.windows.add-slot :as add-slot ;; TODO should not have to reference, need to pull stuff out
     ]))

;; TODO Refactor Carefull of Loowsie subscriptions
;; ^ they are breaking click events
;; breaking on hot reloads

;; NOTE grid indices starts at 1

(def hr-divions 8)                                          ; slot accuracy TODO this should not be manual


(defn t24->t12 [time]
  (let [x (mod time 12)]
    (if (= x 0) 12 x)))


(defn get-rownum [hr]
  (let [start-time @(rf/subscribe [:db-get-in (conj subs/table-view-location :min-time)])]
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


(defn clone-cell [location]
  #(rf/dispatch [:db-assoc-in add-slot/rf-sub-location-data @(rf/subscribe [:db-get-in location])]))


(defn remove-cell [location]
  (let [[conflict-location [second-last-location _]] (split-at (- (count location) 2) location)
        conflict-container @(rf/subscribe [:db-get-in conflict-location])]

    #(if (and (= :items second-last-location) (= (count (:items conflict-container)) 2))
       ;; TODO remove
       (do (println "assoc" (last location) (- 1 (last location)))
           (cljs.pprint/pprint conflict-container)
           (println (get-in conflict-container [:items (- 1 (last location))]))
           (rf/dispatch [:db-assoc-in conflict-location (nth (:items conflict-container) (- 1 (last location)))]))
       (rf/dispatch [:db-update-in (butlast location) vector-remove (last location)]))))


(defn time-cell-summary [location {:keys [start-time end-time abbreviation group]}]
  ^{:key location}
  [:div {:onClick       (clone-cell location)
         :onDoubleClick (remove-cell location)
         :style         {:background-color (or @(rf/subscribe [:db-get-in (conj subs/default-group-colours group)]) "snow")
                         :width            "100%"
                         :font-size        "1em"}}
   [:div.slot {:style {:display         :flex
                       :justify-content :space-between}}
    [:div abbreviation] [:div start-time "-" end-time]]])


(defn time-slot-cell [parent-start parent-end location {:keys [start-time end-time optional group template]
                                                        :or   {template "default"}
                                                        :as   slot}]

  (let [data   (merge optional (apply (partial dissoc slot) subs/template-ignore-keys))
        hiccup (or @(rf/subscribe [:db-get-in [:cell-views template]])
                   [:div.slot.info [:b [:p "{%main-label%}"]] [:p {:style {:color :red}} "no template found"]])]

    [:div.slot-container
     {:onClick       (clone-cell location)
      :onDoubleClick (remove-cell location)

      :style         {:background-color (or @(rf/subscribe [:db-get-in (conj subs/default-group-colours group)]) "snow")
                      :top              (utils/decimal->str-percent (/ (- start-time parent-start)
                                                                       (- parent-end parent-start)))
                      :height           (utils/decimal->str-percent (/ (- end-time start-time)
                                                                       (- parent-end parent-start)))
                      :position         :relative
                      :width            "100%"}}
     (utils/fill-template hiccup data)]))


(defn show-time-block [day-col slot location]
  (let [{:keys [start-time end-time]} slot]
    [:div {:style (style-grid-block day-col start-time end-time)}
     [time-slot-cell start-time end-time location slot]]))


(defn show-conflicts [day-col slot location]
  (let [{:keys [start-time end-time items]} slot]
    [:div {:style (style-grid-block day-col start-time end-time)}
     (if (= (count items) 2)
       [:div {:style {:display         :flex
                      :justify-content :flex-start
                      :height          "100%"}}
        [:div {:style {:width "50%"}} [time-slot-cell start-time end-time (conj location :items 0) (first items)]]
        [:div {:style {:width "50%"}} [time-slot-cell start-time end-time (conj location :items 1) (second items)]]]
       (conj
         [:div.slot.conflict-container {:style {:height  "100%"
                                                :display "grid"}}]
         (doall (map (fn [idx]
                       (time-cell-summary (conj location :items idx) (nth items idx)))
                     (range (count items))))))]))


(defn entries []
  (doall
    (for [[day slots] @(rf/subscribe [:db-get-field :slots])
          idx (range (count slots))]
      (let [{:keys [conflict?] :as slot} (nth slots idx)
            day-col (-> @(rf/subscribe [:db-get-in (conj subs/table-view-location :display-days)])
                        (.indexOf day)
                        (get-colnum))]
        (if conflict?
          ^{:key slot}
          [show-conflicts day-col slot [:slots day idx]]
          ^{:key slot}
          [show-time-block day-col slot [:slots day idx]])))))


(defn load [& [config]]
  [:div#table-view config
   #_(println @(rf/subscribe [:db-get-in subs/table-view-location]))
   (if-let [table-config @(rf/subscribe [:db-get-in subs/table-view-location])]
     (let [{:keys [display-days width height increment min-time max-time]} table-config]
       [:div.table
        {:style {:display               :grid
                 :grid-template-columns (str "5ch repeat(" (count display-days) ", 1fr")
                 :grid-template-rows    (str "2.5em repeat(" (get-rownum max-time) ", 1fr")
                 :width                 width
                 :height                height
                 :margin                :inherit}}
        (table-headers display-days)
        (time-labels min-time max-time increment)
        (entries)])
     [:div])])
