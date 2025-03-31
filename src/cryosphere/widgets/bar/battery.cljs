(ns cryosphere.widgets.bar.battery
  (:require [cryosphere.utils :refer [round bind derive-props]]
            ["gi://Gtk?version=4.0$default" :as Gtk]
            ["gi://AstalBattery$default" :as AstalBattery]))


(def battery (AstalBattery/get_default))
(def label* (derive-props battery [:energy :energy-rate :time-to-empty]
                          (fn [energy energy-rate time-to-empty]
                            (str (round energy 2) "kWh / "
                                 (round energy-rate 2) "kW = "
                                 (round (/ time-to-empty 3600) 2) "h"))))


(defn Battery [_]
  #jsx [:menubutton {:name       :battery
                     :cssClasses [:island]}
        #_[:circularprogress {:cssName    :circ-prog
                              :percentage (bind battery :percentage)
                              :line-width 3
                              :hexpand    true}]
        [:image {:iconName (bind battery :icon-name)}]
        [:popover {:position Gtk/PositionType.GTK_POS_LEFT}
         [:label {:label (bind label*)}]]])
