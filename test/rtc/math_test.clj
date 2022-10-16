(ns rtc.math-test
  "Tests based and expanded the following examples:
  https://floating-point-gui.de/errors/NearlyEqualsTest.java"
  (:require [clojure.test :refer [deftest testing is]]
            [rtc.math :as sut]))

(defn nearly-eq
  ([a b]
   (nearly-eq a b 1e-5))
  ([a b e]
   (sut/eq e a b)))

(defmacro is-not
  [form] `(is (not ~form)))

(deftest eq
  (testing "big"
    (is (nearly-eq 1000000 1000001))
    (is (nearly-eq 1000001 1000000))
    (is-not (nearly-eq 10000 10001))
    (is-not (nearly-eq 10001 10000)))
  (testing "big negative"
    (is (nearly-eq -1000000 -1000001))
    (is (nearly-eq -1000001 -1000000))
    (is-not (nearly-eq -10000 -10001))
    (is-not (nearly-eq -10001 -10000)))
  (testing "mid"
    (is (nearly-eq 1.0000001 1.0000002))
    (is (nearly-eq 1.0000001 1.0000001))
    (is-not (nearly-eq 1.0001 1.0002))
    (is-not (nearly-eq 1.0002 1.0001)))
  (testing "mid negative"
    (is (nearly-eq -1.0000001 -1.0000002))
    (is (nearly-eq -1.0000001 -1.0000001))
    (is-not (nearly-eq -1.0001 -1.0002))
    (is-not (nearly-eq -1.0002 -1.0001)))
  (testing "small"
    (is (nearly-eq 0.000000001000001 0.000000001000002))
    (is (nearly-eq 0.000000001000002 0.000000001000001))
    (is-not (nearly-eq 0.000000000001001 0.000000000001002))
    (is-not (nearly-eq 0.000000000001002 0.000000000001001)))
  (testing "small negative"
    (is (nearly-eq -0.000000001000001 -0.000000001000002))
    (is (nearly-eq -0.000000001000002 -0.000000001000001))
    (is-not (nearly-eq -0.000000000001001 -0.000000000001002))
    (is-not (nearly-eq -0.000000000001002 -0.000000000001001)))
  (testing "small differences"
    (is (nearly-eq 0.3 0.30000003))
    (is (nearly-eq 0.30000003 0.3))
    (is (nearly-eq -0.3 -0.30000003))
    (is (nearly-eq -0.30000003 -0.3)))
  (testing "zero"
    (is (nearly-eq 0 0))
    (is (nearly-eq 0 -0))
    (is (nearly-eq -0 0))
    (is (nearly-eq -0 -0))
    (is-not (nearly-eq 0 0.00000001))
    (is-not (nearly-eq 0.00000001 0))
    (is-not (nearly-eq -0 0.00000001))
    (is-not (nearly-eq 0.00000001 -0))
    (is-not (nearly-eq 0 -0.00000001))
    (is-not (nearly-eq -0.00000001 0))
    (is-not (nearly-eq -0 -0.00000001))
    (is-not (nearly-eq -0.00000001 -0))
    (is (nearly-eq 0 1e-40 0.01))
    (is (nearly-eq 1e-40 0 0.01))
    (is (nearly-eq -0 1e-40 0.01))
    (is (nearly-eq 1e-40 -0 0.01))
    (is (nearly-eq 0 -1e-40 0.01))
    (is (nearly-eq -1e-40 0 0.01))
    (is (nearly-eq -0 -1e-40 0.01))
    (is (nearly-eq -1e-40 -0 0.01))
    (is-not (nearly-eq 0 1e-40  0.000001))
    (is-not (nearly-eq 1e-40 0  0.000001))
    (is-not (nearly-eq -0 1e-40 0.000001))
    (is-not (nearly-eq 1e-40 -0 0.000001))
    (is-not (nearly-eq 0 -1e-40 0.000001))
    (is-not (nearly-eq -1e-40 0  0.000001))
    (is-not (nearly-eq -0 -1e-40 0.000001))
    (is-not (nearly-eq -1e-40 -0 0.000001)))
  (testing "max"
    (is (nearly-eq Float/MAX_VALUE Float/MAX_VALUE))
    (is-not (nearly-eq Float/MAX_VALUE (- Float/MAX_VALUE)))
    (is-not (nearly-eq (- Float/MAX_VALUE) Float/MAX_VALUE))
    (is-not (nearly-eq Float/MAX_VALUE (/ Float/MAX_VALUE 2)))
    (is-not (nearly-eq (/ Float/MAX_VALUE 2) Float/MAX_VALUE))
    (is-not (nearly-eq Float/MAX_VALUE (/ Float/MAX_VALUE -2)))
    (is-not (nearly-eq (/ Float/MAX_VALUE -2) Float/MAX_VALUE))
    (is-not (nearly-eq (- Float/MAX_VALUE) (/ Float/MAX_VALUE 2)))
    (is-not (nearly-eq (/ Float/MAX_VALUE 2) (- Float/MAX_VALUE))))
  (testing "min"
    (is (nearly-eq Float/MIN_VALUE Float/MIN_VALUE))
    (is (nearly-eq Float/MIN_VALUE (- Float/MIN_VALUE)))
    (is (nearly-eq (- Float/MIN_VALUE) Float/MIN_VALUE)))
  (testing "infinities"
    (is (nearly-eq Float/POSITIVE_INFINITY Float/POSITIVE_INFINITY))
    (is (nearly-eq Float/NEGATIVE_INFINITY Float/NEGATIVE_INFINITY))
    (is-not (nearly-eq Float/NEGATIVE_INFINITY Float/POSITIVE_INFINITY))
    (is-not (nearly-eq Float/POSITIVE_INFINITY Float/NEGATIVE_INFINITY))
    (is-not (nearly-eq Float/POSITIVE_INFINITY Float/MAX_VALUE))
    (is-not (nearly-eq Float/POSITIVE_INFINITY (- Float/MAX_VALUE)))
    (is-not (nearly-eq (- Float/POSITIVE_INFINITY) Float/MAX_VALUE))
    (is-not (nearly-eq (- Float/POSITIVE_INFINITY) (- Float/MAX_VALUE)))
    (is-not (nearly-eq Float/MAX_VALUE Float/POSITIVE_INFINITY))
    (is-not (nearly-eq (- Float/MAX_VALUE) Float/POSITIVE_INFINITY))
    (is-not (nearly-eq Float/MAX_VALUE (- Float/POSITIVE_INFINITY)))
    (is-not (nearly-eq (- Float/MAX_VALUE) (- Float/POSITIVE_INFINITY)))
    (is-not (nearly-eq Float/NEGATIVE_INFINITY Float/MAX_VALUE))
    (is-not (nearly-eq Float/NEGATIVE_INFINITY (- Float/MAX_VALUE)))
    (is-not (nearly-eq (- Float/NEGATIVE_INFINITY) Float/MAX_VALUE))
    (is-not (nearly-eq (- Float/NEGATIVE_INFINITY) (- Float/MAX_VALUE)))
    (is-not (nearly-eq Float/MAX_VALUE Float/NEGATIVE_INFINITY))
    (is-not (nearly-eq (- Float/MAX_VALUE) Float/NEGATIVE_INFINITY))
    (is-not (nearly-eq Float/MAX_VALUE (- Float/NEGATIVE_INFINITY)))
    (is-not (nearly-eq (- Float/MAX_VALUE) (- Float/NEGATIVE_INFINITY)))
    (is-not (nearly-eq Float/POSITIVE_INFINITY Float/MIN_VALUE))
    (is-not (nearly-eq Float/POSITIVE_INFINITY (- Float/MIN_VALUE)))
    (is-not (nearly-eq (- Float/POSITIVE_INFINITY) Float/MIN_VALUE))
    (is-not (nearly-eq (- Float/POSITIVE_INFINITY) (- Float/MIN_VALUE)))
    (is-not (nearly-eq Float/MIN_VALUE Float/POSITIVE_INFINITY))
    (is-not (nearly-eq (- Float/MIN_VALUE) Float/POSITIVE_INFINITY))
    (is-not (nearly-eq Float/MIN_VALUE (- Float/POSITIVE_INFINITY)))
    (is-not (nearly-eq (- Float/MIN_VALUE) (- Float/POSITIVE_INFINITY)))
    (is-not (nearly-eq Float/NEGATIVE_INFINITY Float/MIN_VALUE))
    (is-not (nearly-eq Float/NEGATIVE_INFINITY (- Float/MIN_VALUE)))
    (is-not (nearly-eq (- Float/NEGATIVE_INFINITY) Float/MIN_VALUE))
    (is-not (nearly-eq (- Float/NEGATIVE_INFINITY) (- Float/MIN_VALUE)))
    (is-not (nearly-eq Float/MIN_VALUE Float/NEGATIVE_INFINITY))
    (is-not (nearly-eq (- Float/MIN_VALUE) Float/NEGATIVE_INFINITY))
    (is-not (nearly-eq Float/MIN_VALUE (- Float/NEGATIVE_INFINITY)))
    (is-not (nearly-eq (- Float/MIN_VALUE) (- Float/NEGATIVE_INFINITY))))
  (testing "nan"
    (is-not (nearly-eq Float/NaN Float/NaN))
    (is-not (nearly-eq Float/NaN 0))
    (is-not (nearly-eq 0 Float/NaN))
    (is-not (nearly-eq Float/NaN -0))
    (is-not (nearly-eq -0 Float/NaN))
    (is-not (nearly-eq Float/NaN Float/POSITIVE_INFINITY))
    (is-not (nearly-eq Float/POSITIVE_INFINITY Float/NaN))
    (is-not (nearly-eq Float/NaN (- Float/POSITIVE_INFINITY)))
    (is-not (nearly-eq (- Float/POSITIVE_INFINITY) Float/NaN))
    (is-not (nearly-eq Float/NaN Float/NEGATIVE_INFINITY))
    (is-not (nearly-eq Float/NEGATIVE_INFINITY Float/NaN))
    (is-not (nearly-eq Float/NaN (- Float/NEGATIVE_INFINITY)))
    (is-not (nearly-eq (- Float/NEGATIVE_INFINITY) Float/NaN))
    (is-not (nearly-eq Float/NaN Float/MAX_VALUE))
    (is-not (nearly-eq Float/MAX_VALUE Float/NaN))
    (is-not (nearly-eq Float/NaN (- Float/MAX_VALUE)))
    (is-not (nearly-eq (- Float/MAX_VALUE) Float/NaN))
    (is-not (nearly-eq Float/NaN Float/MIN_VALUE))
    (is-not (nearly-eq Float/MIN_VALUE Float/NaN))
    (is-not (nearly-eq Float/NaN (- Float/MIN_VALUE)))
    (is-not (nearly-eq (- Float/MIN_VALUE) Float/NaN)))
  (testing "opposite"
    (is-not (nearly-eq 1.000000001 -1))
    (is-not (nearly-eq -1 1.000000001))
    (is-not (nearly-eq -1.000000001 1))
    (is-not (nearly-eq 1 -1.000000001))
    (is-not (nearly-eq (* 10 Float/MIN_VALUE) (* -10 Float/MIN_VALUE) sut/epsilon))
    (is-not (nearly-eq (* 1000 Float/MIN_VALUE) (* -1000 Float/MIN_VALUE)))
    (is-not (nearly-eq (* 10000 Float/MIN_VALUE) (* -10000 Float/MIN_VALUE))))
  (testing "ulp"
    (is (nearly-eq Float/MIN_VALUE Float/MIN_VALUE))
    (is (nearly-eq Float/MIN_VALUE (- Float/MIN_VALUE)))
    (is (nearly-eq (- Float/MIN_VALUE) Float/MIN_VALUE))
    (is (nearly-eq Float/MIN_VALUE 0))
    (is (nearly-eq 0 Float/MIN_VALUE))
    (is (nearly-eq 0 (- Float/MIN_VALUE)))
    (is (nearly-eq (- Float/MIN_VALUE) 0))
    (is-not (nearly-eq 0.000000001 Float/MIN_VALUE))
    (is-not (nearly-eq 0.000000001 (- Float/MIN_VALUE)))
    (is-not (nearly-eq Float/MIN_VALUE 0.000000001))
    (is-not (nearly-eq (- Float/MIN_VALUE) 0.000000001))))
