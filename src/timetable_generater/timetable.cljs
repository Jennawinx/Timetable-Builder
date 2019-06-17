(ns timetable-generater.timetable
  (:require
    [clojure.string :as s])
  ;; specify table here
  (:use [resources.mar10-2019-study :only (test-table)]))

(def hr-divions 4)                  ; slot accuracy

(defn t24->t12 [time]
  (let [x (mod time 12)]
    (if (= x 0) 12 x)))

(defn load []
  (let [{:keys [meta slots]} test-table
        {:keys [width height display-days course-colours increment]} meta

        day-cols       (->> display-days (count))
        min-start-time (->> (map :start-time slots) (apply min))
        max-end-time   (->> (map :end-time slots) (apply max))
        get-rownum     (fn [hr] (-> hr
                                    (- min-start-time)
                                    (* hr-divions)
                                    (+ 2)))
        get-colnum     (fn [colnum] (inc colnum))
        _              (println min-start-time max-end-time course-colours)]

    [:div.table
     {:style {:display               :grid
              :grid-template-columns (str "5ch repeat(" (dec (get-colnum day-cols)) ", 1fr")
              :grid-template-rows    (str "2em repeat(" (dec (get-rownum max-end-time)) ", 1fr")
              :width                 width
              :height                height}}

     (for [day (into [""] display-days)]
       ^{:key day}
       [:div.day day])

     (for [time (range min-start-time (inc max-end-time) increment)]
       ^{:key time}
       [:div.time
        {:style {:grid-column-start (get-colnum 0)
                 :grid-column-end   (get-colnum (inc 0))
                 :grid-row-start    (get-rownum time)
                 :grid-row-end      (get-rownum (+ time increment))}}
        (str (t24->t12 time))])

     (for [slot slots]
       ^{:key slot}
       (let [{:keys [short-name type no room desc end-time course-code day-of-week start-time]} slot
             colour (or (get course-colours course-code) (get course-colours :default))
             daynum (-> (.indexOf display-days day-of-week) (inc))
             _      (println name)]
         [:div.slot
          {:style {:grid-column-start (get-colnum daynum)
                   :grid-column-end   (get-colnum (inc daynum))
                   :grid-row-start    (get-rownum start-time)
                   :grid-row-end      (get-rownum end-time)
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
     ]))

#_(comment
    (def days-of-the-week [:sun :mon :tue :wed :thu :fri :sat])
    (defn get-table-data [slots]
      "Data transformation, very inefficient???"
      (->> days-of-the-week
           (map
             (fn [day]
               (->>
                 slots
                 (filter #(= day (:day-of-week %)))
                 (map (fn [slot]
                        (let [start-time (:start-time slot)
                              info       (-> slot
                                             (dissoc :start-time)
                                             (dissoc :day-of-week))]
                          (sorted-map start-time #{info}))))
                 (apply merge-with into)
                 (array-map day))))
           (apply merge)))

    "Uses the formated data-structure, originally so you can show overlapping times"
    (defn load []
      (let [{:keys [meta] :as data} test-table
            {:keys [width height display-days course-colours increment]} meta

            day-cols       (->> display-days
                                (count))
            min-start-time (->> (map :start-time (:slots data))
                                (apply min))
            max-end-time   (->> (map :end-time (:slots data))
                                (apply max))
            get-rownum     (fn [hr] (-> hr
                                        (- min-start-time)
                                        (* hr-divions)
                                        (+ 2)))
            get-colnum     (fn [colnum] (inc colnum))
            table-data     (get-table-data (:slots data))
            _              (println min-start-time max-end-time course-colours)]

        [:div {:style {:display               :grid
                       :grid-template-columns (str "8ch repeat(" (dec (get-colnum day-cols)) ", auto")
                       :grid-template-rows    (str "2em repeat(" (dec (get-rownum max-end-time)) ", auto")
                       :width                 width
                       :height                height}}

         (for [day (into [""] display-days)]
           ^{:key day}
           [:div [:div.day.va-middle day]])

         (for [time (range min-start-time max-end-time increment)]
           ^{:key time}
           [:div.time
            {:style {:grid-column-start (get-colnum 0)
                     :grid-column-end   (get-colnum (inc 0))
                     :grid-row-start    (get-rownum time)
                     :grid-row-end      (get-rownum (+ time increment))}}
            (str (t24->t12 time) ":00")])

         (for [day    (range (count display-days))
               [start-time courses] (get table-data (get display-days day))
               course courses]
           ^{:key [day start-time course]}
           (let [{:keys [short-name type no room end-time course-code]} course
                 colour (get course-colours course-code)
                 daynum (inc day)
                 _      (println name)]
             [:div.slot
              {:style {:grid-column-start (get-colnum daynum)
                       :grid-column-end   (get-colnum (inc daynum))
                       :grid-row-start    (get-rownum start-time)
                       :grid-row-end      (get-rownum end-time)
                       :background-color  colour}}
              [:div.info
               [:div.course (str short-name " " (s/upper-case (name type)) no)]
               [:div.room room]
               ]]))
         ]
        )))
