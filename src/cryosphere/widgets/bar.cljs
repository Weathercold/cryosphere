(ns cryosphere.widgets.bar
  (:require [cryosphere.widgets.bar.battery :refer [Battery]]
            [cryosphere.widgets.bar.time :refer [Time]]
            [cryosphere.widgets.bar.hyprland :refer [Workspaces]]
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
           [:box {:cssClasses ["islands"]}
            [Battery]]

           [:box {:cssClasses ["islands"]}
            [Time]]

           [:box {:cssClasses ["islands"]}
            [Workspaces]]]]))
