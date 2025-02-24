(ns cryosphere.widgets.bar
  (:require [cryosphere.widgets.bar.battery :refer [Battery]]
            [cryosphere.widgets.bar.date :refer [Date]]
            ["astal/gtk4" :refer [App Astal Gtk]]))


(defn Bar [monitor]
  (let [{:keys [LEFT TOP BOTTOM]} Astal.WindowAnchor]
    #jsx [:window {:visible true
                   :cssClasses ["Bar"]
                   :gdkmonitor monitor
                   :exclusivity Astal.Exclusivity.EXCLUSIVE
                   :anchor (| LEFT TOP BOTTOM)
                   :application App}
          [:box {:name "root"
                 :vertical true}
           [:box {:cssClasses ["islands"]
                  :valign Gtk.Align.START}
            [Battery]]

           [:box {:cssClasses ["islands"]
                  :valign Gtk.Align.END}
            [Date]]]]))
