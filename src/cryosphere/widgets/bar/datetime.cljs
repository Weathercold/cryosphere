(ns cryosphere.widgets.bar.datetime
  (:require [cryosphere.utils :refer [poll]]
            [cryosphere.widgets.vertical-label :refer [VLabel]]
            ["astal" :refer [bind derive]]
            ["gi://GLib$default" :as GLib]
            ["gi://Gtk?version=4.0$default" :as Gtk]))


(def datetime* (poll 1000 #(GLib/DateTime.new_now_local)))
(def date* (derive [datetime*] #(.format % "%m %d")))
(def time* (derive [datetime*] #(.format % "%H %M")))


(defn Datetime [_]
  #jsx [:menubutton {:name :datetime
                     :cssClasses [:island]}
        [:box {:vertical true}
         [VLabel {:label (bind date*)}]
         [Gtk/Separator]
         [VLabel {:label (bind time*)}]]
        [:popover
         [Gtk/Calendar]]])
