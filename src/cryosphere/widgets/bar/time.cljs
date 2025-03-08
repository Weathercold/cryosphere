(ns cryosphere.widgets.bar.time
  (:require [cryosphere.widgets.vertical-label :refer [VLabel]]
            ["astal" :refer [Variable bind]]
            ["gi://GLib$default" :as GLib]
            ["gi://Gtk$default" :as Gtk]))


(def *time (.poll (Variable "") 1000
                  #(.format (GLib/DateTime.new_now_local) "%m %d ― %H %M")))


(defn Time [_]
  #jsx [:box {:name "time"
              :cssClasses ["island"]
              :vertical true}
        [:menubutton
         [VLabel {:label (bind *time)}]
         [:popover
          [Gtk.Calendar]]]])
