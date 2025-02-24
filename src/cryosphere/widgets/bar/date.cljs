(ns cryosphere.widgets.bar.date
  (:require [cryosphere.widgets.vertical-label :refer [VLabel]]
            ["astal" :refer [Variable bind]]
            ["gi://Gtk$default" :as Gtk]))


(def *time (.poll (Variable "") 1000 "date +'%m %e ― %H %M'"))


(defn Date [_]
  #jsx [:box {:cssClasses ["island"]
              :vertical true}
        [:menubutton {:name "date"
                      :vexpand true}
         [VLabel {:label (bind *time)}]
         [:popover
          [Gtk.Calendar]]]])
