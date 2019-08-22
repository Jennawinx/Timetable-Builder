(ns timetable-generater.views.add-data
  (:require
    [timetable-generater.views.windows.add-slot :as add-slot]
    [timetable-generater.custom-components :as custom]
    [re-frame.core :as rf]))

;; TODO time conflicts
;; TODO overflow
;; TODO lots of ui stuff
;; TODO move temp styling
;; TODO ui height divisions use grid or flex, hardcoding vh is terrible


(defn instructions []
  [:div {:style {:width            "100%"
                 :background-color "lightblue"
                 :padding          "1em"}}
   "Click on cell to clone info
   " [:br]
   "Double Click on cell to delete"])


(defn upload []
  [custom/ui-button
   {:class   "ui button field blue fluid"
    :onClick #()}
   "Upload File"])


(defn save []
  [custom/ui-button
   {:class   "ui button field fluid teal"
    :onClick #()}
   "Save File"])


(defn print-save []
  [:pre (with-out-str (cljs.pprint/pprint (:slots @(rf/subscribe [:db-peek])))) ])


(defn load []
  [:div
   [instructions]
   [:div {:style {:height "65vh"}} [add-slot/load]]
   [upload]
   #_[save]
   [:div {:style {:height "24vh"
                  :overflow :scroll}} [print-save]]])
