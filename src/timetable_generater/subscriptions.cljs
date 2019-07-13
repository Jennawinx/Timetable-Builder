(ns timetable-generater.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    {

     ;; Auto-fill
     :main-labels        #{}
     :groups             #{"test1" "test2"}
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
     :editor             {:add-slot {:required     {}
                                     :optional     {"bugs" "hi"}
                                     :field-to-add ""}}
     }))

(rf/reg-sub
  :db-peek
  (fn [db _]
    db))

(rf/reg-sub
  :db-get-field
  (fn [db [_ field]]
    (get db field)))


;; ---------------- ADD SLOT ----------------


(rf/reg-event-db
  :add-slot/update-required-field
  (fn [db [_ field value]]
    (assoc-in db [:editor :add-slot :required field] value)))

(rf/reg-event-db
  :add-slot/update-optional-field
  (fn [db [_ field value]]
    (assoc-in db [:editor :add-slot :optional field] value)))

(rf/reg-sub
  :add-slot/get-optional-field
  (fn [db [_ field]]
    (get-in db [:editor :add-slot :optional field])))

(rf/reg-sub
  :add-slot/get-field-to-add
  (fn [db [_ field]]
    (get-in db [:editor :add-slot :field-to-add])))

(rf/reg-event-db
  :add-slot/update-field-to-add
  (fn [db [_ field]]
    (assoc-in db [:editor :add-slot :field-to-add] field)))

(rf/reg-event-db
  :add-slot/add-optional-field
  (fn [db _]
    (let [field @(rf/subscribe [:add-slot/get-field-to-add])]
      (if (= (clojure.string/trim field) "")
        db
        (assoc-in db [:editor :add-slot :optional field] "")))))

(rf/reg-sub
  :add-slot/get-optional-fields
  (fn [db _]
    (get-in db [:editor :add-slot :optional] {})))