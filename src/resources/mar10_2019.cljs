(ns resources.mar10-2019)

(def test-table
  {:meta  {:course-colours {:CSCB63H3 "#99FAFA"
                            :CSCB58H3 "#FFACEF"
                            :CSCB58s  "#DD99DD"
                            :MGEA06H3 "#FCC"
                            :CSCC24H3 "#CCDFFF"
                            :COPD12H3 "khaki"
                            :EESA06H3 "#9BEA9B"
                            :blank    ""
                            :default "silver"
                            }
           ;:time-interval 1
           :display-days   [:mon :tue :wed :thu :fri]
           :width          "86vh"
           :height         "100vh"
           :increment      1
           }
   :slots #{
            ; Monday
            {:course-code :CSCB63H3
             :name        "Data Design & Analysis"
             :short-name  "B63"
             :room        "BV 363"
             :type        :tut
             :no          7
             :day-of-week :mon
             :start-time  9
             :end-time    10}
            {:course-code :CSCB63H3
             :name        "Data Design & Analysis"
             :short-name  "B63"
             :room        "SY 110"
             :type        :lec
             :no          1
             :day-of-week :mon
             :start-time  13
             :end-time    14}
            {:course-code :CSCB58s
             :name        "B58"
             :short-name  "B58"
             :room        "Makerspace"
             :type        nil
             :no          nil
             :day-of-week :mon
             :start-time  14
             :end-time    15}
            {:course-code :CSCB58H3
             :name        "Computer Organization"
             :short-name  "B58"
             :room        "SW 143"
             :type        :lec
             :no          2
             :day-of-week :mon
             :start-time  15
             :end-time    17}
            {:course-code :EESA06H3
             :name        "Intro to Planet Earth"
             :short-name  "Geo"
             :room        "AC 223"
             :type        :lec
             :no          1
             :day-of-week :mon
             :start-time  10
             :end-time    12}
            ; Tuesday
            #_{:course-code :MGEA06H3
               :name        "Intro to Macro"
               :short-name  "Mac"
               :room        "Study"
               :type        :lec
               :no          "*"
               :day-of-week :tue
               :start-time  9
               :end-time    11}
            {:course-code :CSCB58H3
             :name        "Computer Organization"
             :short-name  "B58"
             :room        "IC 402"
             :type        :pra
             :no          1
             :day-of-week :tue
             :start-time  18
             :end-time    21}
            ; Wednesday
            {:course-code :MGEA06H3
             :name        "Intro to Macro"
             :short-name  "Mac"
             :room        "IC 130"
             :type        :lec
             :no          1
             :day-of-week :wed
             :start-time  14
             :end-time    15.5}
            {:course-code :CSCB58H3
             :name        "Computer Organization"
             :short-name  "B58"
             :room        "SW 319"
             :type        :lec
             :no          2
             :day-of-week :wed
             :start-time  16
             :end-time    17}
            ; Thursday
            {:course-code :CSCC24H3
             :name        "Principles of Programming Languages"
             :short-name  "C24"
             :room        "BV 473"
             :type        :tut
             :no          5
             :day-of-week :thu
             :start-time  9
             :end-time    11}
            {:course-code :CSCC24H3
             :name        "Principles of Programming Languages"
             :short-name  "C24"
             :room        "SY 110"
             :type        :lec
             :no          1
             :day-of-week :thu
             :start-time  11
             :end-time    13}
            {:course-code :CSCB63H3
             :name        "Data Design & Analysis"
             :short-name  "B63"
             :room        "SW 319"
             :type        :lec
             :no          1
             :day-of-week :thu
             :start-time  15
             :end-time    17}
            {:course-code :CSCB58s
             :name        "Computer Organization"
             :short-name  "B58"
             :room        "*"
             :type        nil
             :no          nil
             :day-of-week :thu
             :start-time  17
             :end-time    19}
            {:course-code nil
             :name        "Zumba"
             :short-name  "Zumba"
             :room        "?"
             :type        nil
             :no          nil
             :day-of-week :thu
             :start-time  19
             :end-time    20}
            {:course-code nil
             :name        "Brian Research Group Meet"
             :short-name  "Brian"
             :room        "?"
             :type        :meet
             :no          nil
             :day-of-week :thu
             :start-time  14
             :end-time    15}
            ;; Friday
            {:course-code :blank
             :day-of-week :fri
             :start-time  8
             :end-time    22}
            }
   })