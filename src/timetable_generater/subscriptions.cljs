(ns timetable-generater.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    {

     ;; Auto-fill
     :main-labels        #{}
     :groups             #{}
     :labels             #{}

     ;; styling
     ;:themes             {}
     ;:cell-views         {}
     ;:table-views        {}

     ;; active
     :current-cell-view  :default
     :current-table-view :default

     ;; data
     :slots              {}

     ;; editor temp & rendering
     :editor             {:add-slot {}}
     }))

(rf/reg-sub
  :db-peek
  (fn [db _]
    db))


;; ---------------- ADD SLOT ----------------

(rf/reg-event-db
  :add-slot/update-field
  (fn [db [_ field value]]
    (assoc-in db [:editor :add-slot field] value)))

(rf/reg-event-db
  :update-field
  (fn [db [_ field value]]
    (assoc-in db [:editor :add-slot field] value)))