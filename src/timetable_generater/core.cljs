(ns timetable-generater.core
    (:require
      [reagent.core :as r]
      [timetable-generater.timetable :as timetable]))

;; -------------------------
;; Views

(defn home-page []
  [timetable/load])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
