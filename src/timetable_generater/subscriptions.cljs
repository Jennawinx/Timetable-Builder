(ns timetable-generater.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    {
     ;; styling
     :groups             {}
     :labels             {}
     :cell-views         {}
     :table-views        {}

     ;; if we rename, we don't have to change references
     :col-name->id       {}
     :cell-views->id     {}
     :table-views->id    {}

     ;; active
     :current-cell-view  :default
     :current-table-view :default

     ;; data
     :slots              {}}))

(rf/reg-sub
  :db-peek
  (fn [db _]
    db))


(def a 3)