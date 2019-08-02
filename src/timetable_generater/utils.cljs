(ns timetable-generater.utils)

(defn element-value [e]
  (.. e -target -value))

(defn string-injection
  "Replaces {%variable%} in strings with it's corresponding value given in var->str
  variable references a string or keyword name to the value in the given map"
  [s var->str]
  (reduce
    (fn [new_s tagged-variable]
      (let [variable (re-find #"(?<=\{%).*?(?=%\})" tagged-variable)]
        (->> (or (get var->str variable)
                 (get var->str (keyword variable)))
             (str)
             (clojure.string/replace new_s tagged-variable))))
    s
    (re-seq #"\{%.*?%\}" s)))

(defn fill-template
  "Template is a hiccup datastructure

  If an element is a string, variable replacements are done on substrings with {%variable%}
  variable references a string or keyword name with the value replacement in the data map"
  [template data]

  (comment
    "Example:"
    (def template
      [:div {:style {:align "{%container alignment%}"}}
       "{%hi%}"
       [:p "{%type%}"]
       [:div "This class is squishy"
        [:p "Your time is better spent in {%class%} {%type%}"]]])

    (def a {"hi"                  "hello"
            "type"                "tut"
            "container alignment" "center"
            :class                "C123"})

    "Returns:"
    [:div
     {:style {:align "center"}}
     "hello"
     [:p "tut"]
     [:div "This class is squishy" [:p "Your time is better spent in C123 tut"]]])

  (clojure.walk/walk
    #(cond
       (string? %) (string-injection % data)
       (coll? %) (fill-template % data)
       :else %)
    identity
    template))