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

(defn zip-with
  [f v1 v2 & vs]
  (loop [xs v1
         ys v2
         zs (seq vs)]
    (let [fs (mapv f xs ys)]
      (if zs
        (recur fs (first zs) (next zs))
        fs))))

(defn element-wise-naive
  [f [x1 y1 z1 w1] [x2 y2 z2 w2]]
  [(f x1 x2)
   (f y1 y2)
   (f z1 z2)
   (f w1 w2)])

(definline element-wise-inline
  [f a b]
  `(let [[x1# y1# z1# w1#] ~a
         [x2# y2# z2# w2#] ~b]
     [(~f x1# x2#)
      (~f y1# y2#)
      (~f z1# z2#)
      (~f w1# w2#)]))

(defmacro element-wise-macro
  [f a b]
  `(let [[x1# y1# z1# w1#] ~a
         [x2# y2# z2# w2#] ~b]
     [(~f x1# x2#)
      (~f y1# y2#)
      (~f z1# z2#)
      (~f w1# w2#)]))

(defmacro element-wise
  [f a b]
  `(let [[x1# y1# z1# w1#] ~a
         [x2# y2# z2# w2#] ~b]
     [(~f x1# x2#)
      (~f y1# y2#)
      (~f z1# z2#)
      (~f w1# w2#)]))

(defn add-map
  [a b]
  (map + a b))

(defn add-naive
  [a b]
  (element-wise-naive + a b))

(defn add-inline
  [a b]
  (element-wise-inline + a b))

(defn add-macro
  [a b]
  (element-wise-macro + a b))

(defn eq
  [a b]
  (every? true? (element-wise float/eq a b)))

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
  (map * a b))

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
