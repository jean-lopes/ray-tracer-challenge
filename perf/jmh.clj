(ns jmh
  (:require [jmh.core :as jmh]))

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

(jmh/run bench-env bench-opts)
