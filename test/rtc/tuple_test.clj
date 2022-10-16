(ns rtc.tuple-test
  (:require [rtc.tuple :as tuple]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]))

(def float-gen
  (gen/let [x (gen/double* {:NaN? false
                            :infinite? false
                            :min Float/MIN_VALUE
                            :max Float/MAX_VALUE})]
    (float x)))

(def point-gen
  (gen/let [x float-gen
            y float-gen
            z float-gen]
    (tuple/point x y z)))

(def vec4-gen
  (gen/let [x float-gen
            y float-gen
            z float-gen]
    (tuple/vec4 x y z)))

(def tuple-gen
  (gen/one-of [point-gen vec4-gen]))

(defspec point
  (prop/for-all
   [x float-gen
    y float-gen
    z float-gen]
   (tuple/eq [x y z 1]
      (tuple/point x y z))))

(defspec vec4
  (prop/for-all
   [x float-gen
    y float-gen
    z float-gen]
   (tuple/eq [x y z 0]
      (tuple/vec4 x y z))))

(defspec add-identity
  (prop/for-all
   [a tuple-gen]
   (tuple/eq a (tuple/add a tuple/zero))))

(defspec add-commutativity
  (prop/for-all
   [a tuple-gen
    b tuple-gen]
   (tuple/eq (tuple/add a b)
      (tuple/add b a))))

(defspec add-associativity
  (prop/for-all
   [a tuple-gen
    b tuple-gen
    c tuple-gen]
   (tuple/eq (tuple/add (tuple/add a b) c)
             (tuple/add a (tuple/add b c)))))

(defspec sub-of-points-is-a-vector
  (prop/for-all
   [a point-gen
    b point-gen]
   (tuple/vec4? (tuple/sub a b))))

(defspec sub-identity
  (prop/for-all
   [a tuple-gen]
   (tuple/eq a (tuple/sub a tuple/zero))))

(defspec sub-zero
  (prop/for-all
   [a tuple-gen]
   (tuple/eq tuple/zero (tuple/sub a a))))

(defspec negate-roundtrip
  (prop/for-all
   [a tuple-gen]
   (tuple/eq a (-> a tuple/negate tuple/negate))))

(defspec mul-identity
  (prop/for-all
   [a tuple-gen]
   (tuple/eq a (tuple/mul a tuple/one))))

(defspec mul-zero
  (prop/for-all
   [a tuple-gen]
   (tuple/eq tuple/zero (tuple/mul a tuple/zero))))

(defspec mul-commutativity
  (prop/for-all
   [a tuple-gen
    b tuple-gen]
   (tuple/eq (tuple/mul a b) (tuple/mul b a))))

(defspec mul-associativity
  (prop/for-all
   [a tuple-gen
    b tuple-gen
    c tuple-gen]
   (tuple/eq (tuple/mul (tuple/mul a b) c)
             (tuple/mul a (tuple/mul b c)))))
