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


(defn reduce-lazy-lookahead
  "A reduce-like function that consumes the next 2 values and stops if the
  result of the applied function is :break

  the given function 'inner-fn' takes form of (fn [result this next] ...)
  and returns a value or :break which stops the next consumption
  if 'coll' has less than 2 values, reduce-lazy-lookahead returns the given 'value'

  Example: Sum all consecutive even pairs

  (reduce-lazy-lookahead
    (fn [result this next]
      (println result this next)
      (if (and (even? this) (even? next))
        (+ result this next)
        result))
    0
    [1 2 2 3 4 5 4 2 4])
  =>
  16

  Example: Sum all consecutive even numbers until the first non-even number

  (reduce-lazy-lookahead
    (fn [result this next]
      (println result this next)
      (if (and (even? this) (even? next))
        (if (= 0 result)
          (+ result this next)
          (+ result next))
        :break))
    0
    [6 2 2 8 3 4 5 4 2 4])
  =>
  18
  "
  ([inner-fn value coll]
   (reduce-lazy-lookahead inner-fn value (first coll) (rest coll)))
  ([inner-fn result this coll]
   (if (empty? coll)
     result
     (let [nxt      (first coll)
           the-rest (rest coll)
           value    (inner-fn result this nxt)]
       (if (= value :break)
         result
         (reduce-lazy-lookahead inner-fn value nxt the-rest))))))
