(ns timetable-generater.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    {

     ;; Auto-fill
     :main-labels        {"hello" "hi"}
     :groups             #{"test1" "test2"}
     :labels             #{"bye"}

     ;; styling
     ;:themes             {}
     ;:cell-views         {}
     ;:table-views        {}

     :table-views        {"default" {:display-days ["wednesday" "monday" "tuesday"]
                                     :width        "86vh"
                                     :height       "100vh"
                                     :increment    1
                                     :min-time     8
                                     :max-time     20}}

     ;; active
     :current-cell-view  :default
     :current-table-view :default

     ;; data
     :slots              {}

     ;; editor temp & rendering
     :editor             {:add-slot {:required     {:group "hi"}
                                     :optional     {"bugs" "hi"
                                                    "fish" "bye"}
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

(rf/reg-sub
  :db-get-in
  (fn [db [_ location]]
    (get-in db location)))

(rf/reg-event-db
  :db-assoc-in
  (fn [db [_ location value]]
    (assoc-in db location value)))

(rf/reg-event-db
  :db-update-in
  (fn [db [_ location function & values]]
    (apply update-in db location function values)))

;; ---------------- ADD SLOT ----------------

;
;(rf/reg-event-db
;  :add-slot/update-required-field
;  (fn [db [_ field value]]
;    (assoc-in db [:editor :add-slot :required field] value)))
;
;(rf/reg-sub
;  :add-slot/get-required-field
;  (fn [db [_ field]]
;    (get-in db [:editor :add-slot :required field])))
;
;(rf/reg-event-db
;  :add-slot/update-optional-field
;  (fn [db [_ field value]]
;    (assoc-in db [:editor :add-slot :optional field] value)))
;
;(rf/reg-sub
;  :add-slot/get-optional-field
;  (fn [db [_ field]]
;    (get-in db [:editor :add-slot :optional field])))

(rf/reg-sub
  :add-slot/get-field-to-add
  (fn [db [_ _]]
    (get-in db [:editor :add-slot :field-to-add])))

(rf/reg-event-db
  :add-slot/update-field-to-add
  (fn [db [_ field]]
    (assoc-in db [:editor :add-slot :field-to-add] field)))

(rf/reg-event-db
  :add-slot/add-optional-field
  (fn [db _]
    (let [field @(rf/subscribe [:add-slot/get-field-to-add])]
      ;; TODO do not have check here
      (if (= (clojure.string/trim field) "")
        db
        (assoc-in db [:editor :add-slot :optional field] "")))))

;(rf/reg-sub
;  :add-slot/get-optional-fields
;  (fn [db _]
;    (get-in db [:editor :add-slot :optional] {})))

(rf/reg-sub
  :add-slot/get-abbreviation
  (fn [db _]
    (get-in db [:main-labels (get-in db [:editor :add-slot :required :main-label])])))
