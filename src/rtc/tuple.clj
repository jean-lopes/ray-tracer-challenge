(ns rtc.tuple
  (:require [rtc.float :as float]))

(def zero
  [0 0 0 0])

(def one
  [1 1 1 1])

(defn point
  [x y z]
  [x y z 1])

(defn vec4
  [x y z]
  [x y z 0])

(defn point?
  [[_x _y _z w]]
  (float/one? w))

(defn vec4?
  [[_x _y _z w]]
  (float/zero? w))

(defn eq
  [a b]
  (every? true? (mapv float/eq a b)))

(defn add
  [a b]
  (mapv + a b))

(defn sub
  [a b]
  (mapv - a b))

(defn negate
  [v]
  (mapv - v))

(defn mul
  [a b]
  (mapv * a b))

(defn mul-by-scalar
  [v s]
  (mapv (partial * s) v))

(defn div
  [a b]
  (map / a b))

(defn magnitude
  [v]
  (->> (map float/square v)
       (reduce +)
       (float/sqrt)))

(defn normalize
  [v]
  (let [m (magnitude v)]
    (div v (map (constantly m) v))))

(defn dot
  [a b]
  (reduce + (mul a b)))

(defn cross
  [[ax ay az _] [bx by bz _]]
  [(- (* ay bz) (* az by))
   (- (* az bx) (* ax bz))
   (- (* ax by) (* ay bx))
   0.0])
