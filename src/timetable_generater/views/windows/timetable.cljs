(ns timetable-generater.views.windows.timetable
  (:require
    [re-frame.core :as rf]
    [timetable-generater.subscriptions]
    [clojure.string :as s])
  (:use [resources.mar10-2019-study :only (test-table)]))

;; TODO fix grid logic
;; grid indices starts at 1
;; TODO pull out start-time?

(def table-view-location [:table-views "default"])
(def hr-divions 4)                                          ; slot accuracy TODO this should not be manual

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

(defn show-time-block [day-col slot]
  (let [{:keys [required optional]} slot
        {:keys [abbreviation main-label group start-time end-time]} required
        {:keys []} optional
        colour "white"                                      ;; Get group colour
        ]
    [:div.slot
     {:style (merge (style-grid-block day-col start-time end-time)
                    {:background-color colour})}
     [:div.info.va-middle
      ;; TODO Show required info
      [:div
       [:span.slot-title abbreviation]]
      ;; TODO Show optional info
      ]]))


(defn show-conflicts [day-col slot]
  (let [{:keys [start-time end-time]} slot
        colour "red"]
    [:div.slot
     {:style (merge (style-grid-block day-col start-time end-time)
                    {:background-color colour})}
     [:div.info.va-middle
      [:div "Conflict!!!"]]]))


(defn entries []
  (doall
    (for [[day slots] @(rf/subscribe [:db-get-field :slots])
          {:keys [conflict] :as slot} slots]
      (let [day-col (-> @(rf/subscribe [:db-get-in (conj table-view-location :display-days)])
                        (.indexOf day)
                        (get-colnum))]
        (if conflict
          ^{:key slot}
          [show-conflicts day-col slot]
          ^{:key slot}
          [show-time-block day-col slot])))))


(defn load [& [config]]
  [:div#table-view config
   (println @(rf/subscribe [:db-get-in table-view-location]))
   (if-let [table-config @(rf/subscribe [:db-get-in table-view-location])]
     (let [{:keys [display-days width height increment min-time max-time]} table-config
           min-start-time min-time
           max-end-time   max-time]
       [:div.table
        {:style {:display               :grid
                 :grid-template-columns (str "5ch repeat(" (count display-days) ", 1fr")
                 :grid-template-rows    (str "2em repeat(" (get-rownum max-end-time) ", 1fr")
                 :width                 width
                 :height                height
                 :margin                :inherit}}
        (table-headers display-days)
        (time-labels min-start-time max-end-time increment)
        (entries)])
     [:div])])