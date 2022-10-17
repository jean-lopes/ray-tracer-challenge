(ns rtc.float
  "Floating point functions")

(def ^:dynamic *epsilon*
  "Constant used on float equality as threshold when accouting for precision errors"
  (float 1e-5))

(defn eq
  ^Boolean [^Float a ^Float b]
  (< (abs (- a b)) *epsilon*))

(defn pow
  [n e]
  (java.lang.Math/pow n e))

(defn sqrt
  [x]
  (java.lang.StrictMath/sqrt x))
