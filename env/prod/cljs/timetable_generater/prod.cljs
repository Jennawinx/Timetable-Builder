(ns timetable-generater.prod
  (:require
    [timetable-generater.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
