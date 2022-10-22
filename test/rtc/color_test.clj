(ns rtc.color-test
  (:require [rtc.color :as color]
            [rtc.float :as float]
            [rtc.tuple :as tuple]
            [clojure.test :as t]))

(t/deftest component-get
  (let [c1 [0.9 0.6 0.7]]
    (t/is (float/eq 0.9 (color/red   c1)))
    (t/is (float/eq 0.6 (color/green c1)))
    (t/is (float/eq 0.7 (color/blue  c1)))))

(t/deftest adding-colors
  (let [c1 [0.9 0.6 0.75]
        c2 [0.7 0.1 0.25]]
    (t/is (tuple/eq [1.6 0.7 1.0]
                    (tuple/add c1 c2)))))

(t/deftest subtracting-colors
  (let [c1 [0.9 0.6 0.75]
        c2 [0.7 0.1 0.25]]
    (t/is (tuple/eq [0.2 0.5 0.5]
                    (tuple/sub c1 c2)))))

(t/deftest multiplying-a-color-by-scalar
  (let [c1 [0.2 0.3 0.4]]
    (t/is (tuple/eq [0.4 0.6 0.8]
                    (tuple/mul-by-scalar c1 2)))))

(t/deftest multiplying-a-colors
  (let [c1 [1.0 0.2 0.4]
        c2 [0.9 1.0 0.1]]
    (t/is (tuple/eq [0.9 0.2 0.04]
                    (tuple/mul c1 c2)))))
