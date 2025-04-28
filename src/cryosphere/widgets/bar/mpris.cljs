(ns cryosphere.widgets.bar.mpris
  (:require [cryosphere.utils :refer [bind derive-vprops]]
            [cryosphere.widgets.labels :refer [RotatedLabel]]
            [cryosphere.widgets.bar.cava :refer [Cava]]
            ["astal" :refer [exec]]
            ["astal/gtk4" :refer [App]]
            ["gi://AstalMpris$default" :as AstalMpris]
            ["gi://Gtk?version=4.0$default" :as Gtk]))


(def mpris (AstalMpris/get_default))
(def player* (bind mpris :players
                   (fn [players] (some #(when (.get_can_play %) %) players))))
(def title* (derive-vprops player* [:title] #(or % "")))
(def artist* (derive-vprops player* [:artist] #(or % "")))
(def palette* (derive-vprops player* [:cover-art]
                             #(when %
                                (->> (str "matugen image -j hex " %)
                                     exec js/JSON.parse))))
(def gradient* (bind palette* (fn [{{{:keys [primary tertiary]} :dark} :colors}]
                                [primary tertiary])))


(defn- apply_gradient [[from to]]
  (when (and from to)
    (App.apply_css
     (str "#mpris { background-image: linear-gradient(-135deg, " from " 80px, " to "); }"))))

(defn Mpris [_]
  #jsx [:box {:name       :mpris
              :cssClasses [:island]
              :visible    (bind player* some?)
              :vertical   true}
        [Cava]
        [:box
         [RotatedLabel {:label (bind title*)
                        :valign Gtk/Align.START}]
         [RotatedLabel {:label (bind artist*)
                        :valign Gtk/Align.START}]]])


(.subscribe gradient* apply_gradient)
(apply_gradient (.get gradient*))
