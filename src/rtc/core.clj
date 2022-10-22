(ns rtc.core
  (:require [rtc.canvas :as canvas]
            [rtc.ppm :as ppm]
            [rtc.tuple :as tuple]))
(def start
  (tuple/point 0 1 0))

(def velocity
  (-> (tuple/vec4 1 1.8 0)
      (tuple/normalize)
      (tuple/mul-by-scalar 11.25)))

(def projectile
  {:position start
   :velocity velocity})

(def gravity
  (tuple/vec4 0 -0.1 0))

(def wind
  (tuple/vec4 -0.01 0 0))

(def environment
  {:gravity gravity
   :wind wind})

(def canvas
  (canvas/new {:width 900 :height 500}))

(defn tick
  [{:keys [gravity wind]} {:keys [position velocity]}]
  {:position (tuple/add position velocity)
   :velocity (tuple/add (tuple/add velocity gravity) wind)})

(defn visible? [width height {:keys [position]}]
    (let [[x y _ _] position]
      (and (< x width)
           (< y height)
           (>= x 0)
           (>= y 0))))

(defn simulate
  [{:keys [width height environment] :as data} projectile]
  (if (visible? width height projectile)
    (lazy-seq (cons projectile
                    (simulate data (tick environment projectile))))
    nil))

(defn draw-pixels [canvas positions color]
  (loop [ps (seq positions)
         c  canvas]
    (if-let [[x y _ _] (first ps)]
      (recur (next ps)
             (canvas/write-pixel c (int y) (int x) color))
      c)))

(defn -main [& _]
  (let [canvas (canvas/new {:width 900 :height 500})
        context (assoc canvas :environment environment)
        positions (mapv :position (simulate context projectile))]
    (-> canvas
        (draw-pixels positions (tuple/normalize [102 0 102]))
        (ppm/spit-ppm! "sample.ppm"))))

(comment
  (-main)
  ,)
