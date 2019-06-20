(ns timetable-generater.timetable
  (:require
    [clojure.string :as s])
  ;; specify table here
  (:use [resources.mar10-2019-study :only (test-table)]))

(def hr-divions 4)                                          ; slot accuracy

(defn t24->t12 [time]
  (let [x (mod time 12)]
    (if (= x 0) 12 x)))

(defn get-rownum [start-time hr]
  (-> hr
      (- start-time)
      (* hr-divions)
      (+ 2)))

(defn get-colnum [colnum]
  (inc colnum))

(defn table-headers [headers]
  (for [header (into [""] headers)]
    ^{:key header}
    [:div.day header]))

(defn time-labels [start-time end-time increment]
  (for [time (range start-time (inc end-time) increment)]
    ^{:key time}
    [:div.time
     {:style {:grid-column-start (get-colnum 0)
              :grid-column-end   (get-colnum (inc 0))
              :grid-row-start    (get-rownum start-time time)
              :grid-row-end      (get-rownum start-time (+ time increment))}}
     (str (t24->t12 time))]))

(defn load []
  [:div#table-view
   (let [{:keys [meta slots]} test-table
         {:keys [width height display-days course-colours increment]} meta

         day-cols (->> display-days (count))
         min-start-time (->> (map :start-time slots) (apply min))
         max-end-time (->> (map :end-time slots) (apply max))
         _ (println min-start-time max-end-time course-colours)]

     [:div.table
      {:style {:display               :grid
               :grid-template-columns (str "5ch repeat(" (dec (get-colnum day-cols)) ", 1fr")
               :grid-template-rows    (str "2em repeat(" (dec (get-rownum min-start-time max-end-time)) ", 1fr")
               :width                 width
               :height                height}}

      (table-headers display-days)
      (time-labels min-start-time max-end-time increment)

      (for [slot slots]
        ^{:key slot}
        (let [{:keys [short-name type no room desc end-time course-code day-of-week start-time]} slot
              colour (or (get course-colours course-code) (get course-colours :default))
              daynum (-> (.indexOf display-days day-of-week) (inc))
              _ (println short-name)]
          [:div.slot
           {:style {:grid-column-start (get-colnum daynum)
                    :grid-column-end   (get-colnum (inc daynum))
                    :grid-row-start    (get-rownum min-start-time start-time)
                    :grid-row-end      (get-rownum min-start-time end-time)
                    :background-color  colour}}
           [:div.info.va-middle
            [:div
             [:span.slot-title short-name]
             [:span.slot-title.course-type (when-not (nil? type)
                                             (str " " (s/upper-case (name type))))]
             [:span.slot-title.course-type " " no]]
            [:div.room room]
            (when-not (nil? desc)
              [:div.desc {:class (when-not (nil? short-name) "border-top")}
               (interpose [:br] (clojure.string/split-lines desc))])]]))
      ])])
