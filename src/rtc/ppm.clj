(ns rtc.ppm
  (:require [clojure.string :as string]))

(defn- unlines
  ^String [^clojure.lang.PersistentVector lines]
  (let [newline (System/lineSeparator)]
    (str (string/join newline lines) newline)))

(defn- unwords
  ^String [^clojure.lang.PersistentVector words]
  (string/join " " words))

(defn- clamp
  ^Long [^Long lower ^Long upper n]
  (cond
    (> n upper) upper
    (< n lower) lower
    :else n))

(defn <-float-rgb-component
  ^Long [^Long maximum d]
  (->> (* maximum d)
       double
       java.lang.Math/round
       (clamp 0 maximum)))

(defn <-canvas
  [{:keys [width height canvas]}]
  (let [color-max-int 255
        pad (fn pad [n] (format (str "%" (count (str color-max-int)) "d") n))
        header [["P3"] (mapv str [width height]) [(str color-max-int)]]]
    (->> (flatten canvas)
         (mapv (comp pad (partial <-float-rgb-component color-max-int)))
         (partition 15)
         (map vec)
         (concat header)
         vec)))

(defn save-ppm!
  [data path]
  (->> (map unwords data)
       unlines
       (spit path)))

(defn spit-ppm!
  [canvas path]
  (-> (<-canvas canvas)
      (save-ppm! path)))
