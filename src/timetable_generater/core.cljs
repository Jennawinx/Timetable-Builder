(ns timetable-generater.core
    (:require
      [reagent.core :as r]
      [timetable-generater.timetable :as timetable]
      [timetable-generater.views.windows.add-slot :as add-slot]))

;; -------------------------
;; Views

(defn home-page []
  #_[timetable/load]
  [add-slot/load]
  )

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
