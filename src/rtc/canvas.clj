(ns rtc.canvas)

(defn new
  [{:keys [width height]}]
  {:width width
   :height height
   :canvas (vec (repeat height (vec (repeat width [0 0 0]))))})

(defn write-pixel
  [{:keys [width height] :as ctx} row column color]
  (let [r (- height row)
        c (- width column)]
    (update ctx :canvas #(assoc-in % [r c] color))))


(defn read-pixel
  [{:keys [width height canvas]} row column]
  (let [r (- height row)
        c (- width column)]
    (get-in canvas [r c])))
