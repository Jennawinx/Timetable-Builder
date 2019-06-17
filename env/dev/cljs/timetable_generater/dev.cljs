(ns ^:figwheel-no-load timetable-generater.dev
  (:require
    [timetable-generater.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
