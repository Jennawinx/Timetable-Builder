(ns timetable-generater.core
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [timetable-generater.views.add-data :as add-data]
    [timetable-generater.subscriptions]))

;; -------------------------
;; Views

(defn home-page []
  [add-data/load])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root)
  (rf/dispatch-sync [:initialize-db])
  (cljs.pprint/pprint @(rf/subscribe [:db-peek])))