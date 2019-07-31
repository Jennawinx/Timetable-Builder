(ns timetable-generater.views.add-data
  (:require
    [timetable-generater.views.windows.add-slot :as add-slot]
    [timetable-generater.views.windows.timetable :as timetable]))

;; TODO time conflicts
;; TODO overflow
;; TODO lots of ui stuff

(defn load []
  [:div.ui.celled.grid {:style {:height "100vh"             ;; TEMP
                                :margin 0}}
   [:div.row {:style {:height "100%"}}
    [:div.column.ten.wide {:style {:padding 0
                                   :height  "100%"}}
     [timetable/load {:style {:margin :auto
                              :height "100%"}}]]
    [:div.column.six.wide {:style {:padding 0
                                   :height  "100%"}}
     [add-slot/load]]]])
