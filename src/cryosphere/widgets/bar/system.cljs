(ns cryosphere.widgets.bar.system
  (:require [cryosphere.widgets.vertical-label :refer [VLabel]]
            ["gi://Gtk?version=4.0$default" :as Gtk]))

(defn System [_]
  #jsx [:box {:name :system
              :cssClasses [:island]
              :vertical true}
        [:label {:label "󱄅"}]])
