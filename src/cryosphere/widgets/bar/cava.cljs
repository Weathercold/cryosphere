(ns cryosphere.widgets.bar.cava
  (:require [cryosphere.widgets.misc :refer [DrawingArea]]
            ["astal" :refer [bind]]
            ["cairo$default" :as Cairo]
            ["gi://AstalCava$default" :as AstalCava]))


(def nbars 10)
(def cava (doto (AstalCava/get_default)
            (.set_bars (* nbars 2))
            (.set_stereo true)))


(defn- render [widget cr width height]
  (let [bars  (partition nbars (.get_values cava)) ; Left & right channels
        color (.get_color widget)]
    (doto cr
      (.setLineWidth 3)
      (.setLineCap Cairo/LineCap.ROUND)
      (.setSourceRGBA color.red color.green color.blue color.alpha))
    (loop [i 0]
      (when (< i nbars)
        (let [y  (-> (/ nbars 2) (- i 0.5) (* 6) (+ (/ height 2))) ; Bottom to top
              x1 (-> (first bars) (nth i) (* -10) (+ (/ width 2)))
              x2 (-> (second bars) (nth i) (* 10) (+ (/ width 2)))]
          (.moveTo cr x1 y)
          (.lineTo cr x2 y))
        (recur (inc i)))))
  (.stroke cr))

(defn Cava [_]
  #jsx [:box {:name       :cava
              :cssClasses [:island]
              :vertical   true}
        [DrawingArea {:height 80
                      :onDraw render
                      :setup  (fn [widget] (.subscribe (bind cava :values)
                                                       #(widget.queue_draw)))}]])
