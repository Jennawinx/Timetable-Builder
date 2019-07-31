(ns resources.mar10-2019-study)

(def test-table
  {:meta  {:course-colours {:CSCB63H3  "gray "
                            :CSCB58H3  "gray"
                            :MGEA06H3  "gray"
                            :CSCC24H3  "gray"
                            :EESA06H3  "gray"

                            ; Study
                            :CSCB63s   "#99FAFA"
                            :CSCB58s   "#DD99DD"
                            :EESA06s   "#9BEA9B"
                            :MGEA06s   "#FCC"
                            :CSCC24s   "#CCDFFF"

                            ;; Bus
                            :CSCB63b   "#467"
                            :CSCB58b   "#858"
                            :EESA06b   "#363"
                            :MGEA06b   "#B99"
                            :CSCC24b   "#89B"

                            :FRIDAY    "#CFBFFF"
                            :SATURADAY "#FECDAB"
                            :SUNDAY    "#BAEEAD"

                            :default   "gray"
                            :empty     "#DDD"}
           :display-days   [:mon :tue :wed :thu :other-days]
           :width          "86vh"
           :height         "100%"                           ;"100vh"
           :increment      1}
   :slots #{
            ; -----------------------------------------------------
            ; Monday
            ; -----------------------------------------------------
            {:course-code :CSCB63b
             :name        "Data Design & Analysis"
             :short-name  "B63"
             :type        :bus
             :desc        "* Quiz Review"
             :day-of-week :mon
             :start-time  8
             :end-time    9}
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
             :type        :proj
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
            {:course-code :CSCB63s
             :name        "Data Design & Analysis"
             :short-name  "B63"
             :type        :study
             :desc        "* Lec Review
                           * Assignment"
             :day-of-week :mon
             :start-time  19
             :end-time    21}
            {:course-code :EESA06s
             :name        "Intro to Planet Earth"
             :short-name  "Geo"
             :room        "AC 223"
             :desc        "* Modules"
             :type        :lec
             :no          1
             :day-of-week :mon
             :start-time  10
             :end-time    12}
            {:course-code :CSCB58b
             :name        "B58"
             :short-name  "B58"
             :type        "Bus"
             :desc        "* Review Lec"
             :day-of-week :mon
             :start-time  17
             :end-time    18.25}
            ; -----------------------------------------------------
            ; Tuesday
            ; -----------------------------------------------------
            {:course-code :CSCB58H3
             :name        "Computer Organization"
             :short-name  "B58"
             :room        "IC 402"
             :type        :pra
             :no          1
             :day-of-week :tue
             :start-time  18
             :end-time    21}
            {:course-code :MGEA06s
             :name        "Intro to Macro"
             :short-name  "Mac"
             :type        :study
             :desc        "* Lec Notes
                           * Tut Prac
                           * Tut Notes"
             :day-of-week :tue
             :start-time  8.5
             :end-time    10.5}
            {:course-code :CSCC24s
             :name        "Principles of Programming Languages"
             :short-name  "C24"
             :room        "BV 473"
             :type        :study
             :desc        "* Review"
             :day-of-week :tue
             :start-time  10.5
             :end-time    12}
            {:course-code :CSCB63s
             :name        "Data Design & Analysis"
             :short-name  "B63"
             :type        :study
             :desc        "* Assignment"
             :day-of-week :tue
             :start-time  13.5
             :end-time    16}
            {:course-code :EESA06b
             :name        "Intro to Planet Earth"
             :short-name  "Geo"
             :desc        "Read Notes"
             :type        :bus
             :day-of-week :tue
             :start-time  (+ 12 4)
             :end-time    (+ 12 5)}
            ; -----------------------------------------------------
            ; Wednesday
            ; -----------------------------------------------------
            {:course-code :CSCC24b
             :name        "Principles of Programming Languages"
             :short-name  "C24"
             :type        :bus
             :desc        "* Brainstorm"
             :day-of-week :wed
             :start-time  8
             :end-time    9}
            {:course-code :CSCC24s
             :name        "Principles of Programming Languages"
             :short-name  "C24"
             :type        :study
             :desc        "* Assignment"
             :day-of-week :wed
             :start-time  9
             :end-time    10}
            {:course-code :MGEA06s
             :name        "Intro to Macro"
             :short-name  "Mac"
             :room        "IC 130"
             :type        :lec
             :desc        "* Lec Notes
                           * My Notes
                           * Read Notes"
             :no          1
             :day-of-week :wed
             :start-time  14
             :end-time    16}
            {:course-code :CSCB58H3
             :name        "Computer Organization"
             :short-name  "B58"
             :room        "SW 319"
             :type        :lec
             :no          2
             :day-of-week :wed
             :start-time  16
             :end-time    17}
            {:course-code :CSCB58b
             :name        "B58"
             :short-name  "B58"
             :type        "Bus"
             :desc        "* Review Lec"
             :day-of-week :wed
             :start-time  17
             :end-time    18.25}
            {:course-code :CSCB63s
             :name        "Data Design & Analysis"
             :short-name  "B63"
             :type        :study
             :desc        "* Assignment"
             :day-of-week :wed
             :start-time  10
             :end-time    12}
            {:course-code :empty
             :name        "Empty"
             :short-name  "*"
             :type        :study
             :desc        "*"
             :day-of-week :wed
             :start-time  19
             :end-time    21}
            ; -----------------------------------------------------
            ; Thursday
            ; -----------------------------------------------------
            {:course-code :CSCC24s
             :name        "Principles of Programming Languages"
             :short-name  "C24"
             :room        "BV 473"
             :type        :tut
             :desc        "* Readings
                           * Lab
                           * Assignment"
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
             :end-time    16.5}
            {:course-code :CSCB58s
             :name        "Computer Organization"
             :short-name  "B58"
             :room        nil
             :desc        "* Brainstorm
                           * Code"
             :type        :proj
             :no          nil
             :day-of-week :thu
             :start-time  17.5
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
            {:course-code :CSCB63b
             :name        "Data Design & Analysis"
             :short-name  "B63"
             :type        :bus
             :desc        "* Lec Review"
             :day-of-week :thu
             :start-time  20
             :end-time    21}
            {:course-code nil
             :name        "Brian Research Group Meet"
             :short-name  "Brian"
             :room        "?"
             :type        :meet
             :no          nil
             :day-of-week :thu
             :start-time  14
             :end-time    15}
            ; -----------------------------------------------------
            ; Other Days
            ; -----------------------------------------------------
            {:course-code :FRIDAY
             :name        "Friday"
             :short-name  "Friday"
             :desc        "Mac Assign

                           --------------------
                           B58

                           --------------------


                          "
             :day-of-week :other-days
             :start-time  9
             :end-time    12}
            {:course-code :SATURADAY
             :name        "Saturaday"
             :short-name  "Sat"
             :desc        "
                           B63 Review
                           B63 Assign


                           --------------------
                           *

                           --------------------
                           Geo Module
                          "
             :day-of-week :other-days
             :start-time  13
             :end-time    17}
            {:course-code :SUNDAY
             :name        "Sunday"
             :short-name  "Sunday"
             :desc        "
                           C24 Assign

                           --------------------

                           B58

                           --------------------
                           *
                          "
             :day-of-week :other-days
             :start-time  18
             :end-time    22}
            }
   })