(ns timetable-generater.core
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [timetable-generater.timetable :as timetable]
    [timetable-generater.views.windows.add-slot :as add-slot]
    [timetable-generater.subscriptions]))

;; -------------------------
;; Views

(defn home-page []
  #_[timetable/load]
  [add-slot/load])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root)
  (rf/dispatch-sync [:initialize-db])
  (cljs.pprint/pprint @(rf/subscribe [:db-peek])))