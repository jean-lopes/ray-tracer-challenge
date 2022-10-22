(ns core
  (:require [criterium.core :as criterium]
            [clj-memory-meter.core :as memory-meter]
            [jmh.core :as jmh]
            [rtc.tuple :as tuple]))

(def bench-env
  {:benchmarks
   [{:name :add-map    :fn 'rtc.tuple/add-map :args [:state/int-array :state/int-array]}]

   :states
   {:int-array
    {:scope :benchmark
     :trial {:setup {:fn '(comp int-array range)
                     :args [:param/size]}}}}

   :params
    {:edn [#inst "2017-08-05" {:magic 0xcafebabe}]
     :size 4}})

(def bench-opts
  {:type :quick
   :params {:size [4]}
   :profilers ["gc"]})

(comment
  (jmh/run bench-env bench-opts)

  ,)

(comment
  (criterium/bench (tuple/zip-with + [1 1 1 1] [1 1 1 1]))
  (criterium/bench (tuple/add [1 1 1 1] [1 1 1 1]))

  (memory-meter/measure (tuple/add-map    [1 1 1 1] [1 1 1 1]))
  (memory-meter/measure (tuple/add-naive  [1 1 1 1] [1 1 1 1]))
  (memory-meter/measure (tuple/add-inline [1 1 1 1] [1 1 1 1]))
  (memory-meter/measure (tuple/add-macro  [1 1 1 1] [1 1 1 1]))

  (do
    (println "add-map")
    (criterium/bench (tuple/add-map    [1 1 1 1] [1 1 1 1]))
    (println "add-naive")
    (criterium/bench (tuple/add-naive  [1 1 1 1] [1 1 1 1]))
    (println "add-inline")
    (criterium/bench (tuple/add-inline [1 1 1 1] [1 1 1 1]))
    (println "add-macro")
    (criterium/bench (tuple/add-macro  [1 1 1 1] [1 1 1 1])))




  ,)
