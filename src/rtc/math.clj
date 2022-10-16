(ns rtc.math
  "Floating point functions")

(def epsilon
  "Constant used on float equality as threshold when accouting for precision errors"
  (java.lang.Math/ulp (float 1.0)))

(defn eq
  "Compare two floating point numbers within an accepted precision.
  Default precision is `rtc.math/epsilon`"
  ([a b]
   (eq epsilon a b))
  ([e a b]
   (if (= a b)
     true
     (let [abs-a       (abs a)
           abs-b       (abs b)
           diff        (abs (- a b))
           abs-a+abs-b (+ abs-a abs-b)]
       (if (or (zero? a) (zero? b) (< abs-a+abs-b Float/MIN_NORMAL))
         (< diff (* e Float/MIN_NORMAL))
         (< (/ diff (min abs-a+abs-b Float/MAX_VALUE)) e))))))
