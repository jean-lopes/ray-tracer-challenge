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
  (not (pos? w)))

(defn vec4?
  [[_x _y _z w]]
  (zero? w))

(defn element-wise
  "Apply `f` to each pair of elements"
  [f [x1 y1 z1 w1] & [[x2 y2 z2 w2]]]
  [(f x1 x2)
   (f y1 y2)
   (f z1 z2)
   (f w1 w2)])

(defn eq
  [a b]
  (every? true? (element-wise float/eq a b)))

(defn add
  [a b]
  (element-wise + a b))

(defn sub
  [a b]
  (element-wise - a b))

(defn negate
  [a]
  (mapv - a))
