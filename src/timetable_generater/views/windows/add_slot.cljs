(ns timetable-generater.views.windows.add-slot)

(defn derive-key [label]
  ;; lowercase, no space, no colons
  )

(defn field [label type]
  [:div
   [:label label]
   [:input type]])

(defn load-optional-fields []
  [field "Example Hardcoded Optionals" {:type :textbox}])

(defn load []
  [:div#add-slot
   [field "Main Label" {:type :textbox}]
   [field "Group" {:type :textbox}]
   [field "Start Time" {:type :textbox}]
   [field "End Time" {:type :textbox}]
   [:hr]
   [load-optional-fields]
   [:idv
    [field "Add Field" {:type :textbox}]
    [:input {:type :button}]]])
