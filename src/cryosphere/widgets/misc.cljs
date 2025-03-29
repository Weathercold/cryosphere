(ns cryosphere.widgets.misc
  (:require ["astal/gtk4" :refer [astalify]]
            ["gi://Gtk?version=4.0$default" :as Gtk]))


(defn DrawingArea [{:keys [onDraw width height] :as opts}]
  (let [opts' (dissoc opts :onDraw :width :height)
        drawing-area ((astalify Gtk/DrawingArea) opts')]
    (when (some? onDraw) (.set_draw_func drawing-area onDraw))
    (when (some? width) (.set_content_width drawing-area width))
    (when (some? height) (.set_content_height drawing-area height))
    drawing-area))
