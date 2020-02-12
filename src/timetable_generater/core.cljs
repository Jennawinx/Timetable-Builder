(ns timetable-generater.core
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [timetable-generater.views.add-data :as add-data]
    [timetable-generater.views.windows.timetable :as timetable]
    [timetable-generater.subscriptions]))

;; -------------------------
;; Views

(defn home-page []

  [:div.ui.celled.grid {:style {:height "100vh"             ;; TEMP
                                :margin 0}}
   [:div.row {:style {:height "100%"}}
    ;; LEFT side
    [:div.column.ten.wide {:style {:padding 0
                                   :height  "100%"
                                   :background-color "darkgrey"}}

     [timetable/load {:style {:margin :auto
                              :height "100%"}}]]
    ;; RIGHT side
    [:div.column.six.wide {:style {:padding 0
                                   :height  "100%"}}


     [add-data/load]]]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root)
  (rf/dispatch-sync [:initialize-db])
  (cljs.pprint/pprint @(rf/subscribe [:db-peek])))