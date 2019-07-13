(ns timetable-generater.utils)

(defn element-value [e]
  (.. e -target -value))
