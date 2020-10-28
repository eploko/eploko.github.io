(ns plokodelika.toolbelt
  (:require
   [clojure.string :as string]))

(defn cx
  "Converts its `args` to a string suitable to use for the `class` HTML attribute.

  ```clojure
  (class-names \"foo\" \"bar\" :bax { :active true :seleced false })
  -> \"foo bar bax active\"
  (class-names \"foo\" \"bar\" nil)
  -> \"foo bar\"
  (class-names \"foo\" \"bar\" :bax { :active true :seleced false } 'nox { :more-classes-in-map true })
  -> \"foo bar bax active nox more-classes-in-map\"
  ```
  "
  [& args]
  (string/join " "
    (mapv name
      (reduce (fn [arr arg]
        (cond
          (or (string? arg)
              (symbol? arg)
              (keyword? arg)) (conj arr arg)
          (vector? arg) (vec (concat arr arg))
          (map? arg) (vec (concat arr
                            (reduce-kv (fn [map-arr key value]
                              (if (true? value)
                                (conj map-arr key)
                                map-arr)) [] arg)))
          :else arr)) [] args))))
