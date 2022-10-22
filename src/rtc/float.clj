(ns rtc.float
  "Floating point functions"
  (:refer-clojure :exclude [zero?]))

(def ^:dynamic *epsilon*
  "Constant used on float equality as threshold when accouting for precision errors"
  (float 1e-5))

(defn eq
  ^Boolean [^Float a ^Float b]
  (< (abs (- a b)) *epsilon*))

(defn zero?
  ^Boolean [^Float a]
  (eq 0.0 a))

(defn one?
  ^Boolean [^Float a]
  (eq 1.0 a))

(defn pow
  [n e]
  (java.lang.Math/pow n e))

(defn square
  [n]
  (java.lang.Math/pow n 2))

(defn sqrt
  [x]
  (java.lang.StrictMath/sqrt x))


(defn my-fn
  [a]
  (tap> a)
  (tap> "oi"))

(comment
  (tap> 1)
  (my-fn {:abc 1})

  )
