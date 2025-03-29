(ns cryosphere.widgets.bar
  (:require [cryosphere.widgets.bar.hyprland :refer [Workspaces]]
            [cryosphere.widgets.bar.cava :refer [Cava]]
            [cryosphere.widgets.bar.datetime :refer [Datetime]]
            [cryosphere.widgets.bar.battery :refer [Battery]]
            [cryosphere.widgets.bar.system :refer [System]]
            ["astal/gtk4" :refer [App Astal]]
            ["gi://Gtk?version=4.0$default" :as Gtk]))


(defn Bar [monitor]
  (let [{:keys [LEFT TOP BOTTOM]} Astal.WindowAnchor]
    #jsx [:window {:visible true
                   :cssClasses ["Bar"]
                   :gdkmonitor monitor
                   :exclusivity Astal.Exclusivity.EXCLUSIVE
                   :anchor (| LEFT TOP BOTTOM)
                   :application App}
          [:centerbox {:name "root"
                       :orientation Gtk/Orientation.VERTICAL}
           [:box {:cssClasses ["islands"]
                  :vertical true}
            [Workspaces]]

           [:box {:cssClasses ["islands"]
                  :vertical true}
            [Cava]
            [Datetime]]

           [:box {:cssClasses ["islands"]
                  :vertical true}
            [Battery]
            [System]]]]))
