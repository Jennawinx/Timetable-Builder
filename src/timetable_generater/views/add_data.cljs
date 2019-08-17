(ns timetable-generater.views.add-data
  (:require
    [timetable-generater.views.windows.add-slot :as add-slot]))

;; TODO time conflicts
;; TODO overflow
;; TODO lots of ui stuff
;; TODO move temp styling


(defn instructions []
  [:div {:style {:width            "100%"
                 :background-color "lightblue"
                 :padding          "1em"}}
   "Click on cell to clone info
   " [:br]
   "Double Click on cell to delete"])


(defn load []
  [:div
   [instructions]
   [add-slot/load]])
