(ns cryosphere.widgets.bar.datetime
  (:require [cryosphere.utils :refer [bind poll]]
            [cryosphere.widgets.labels :refer [WrappedLabel]]
            ["gi://GLib$default" :as GLib]
            ["gi://Gtk?version=4.0$default" :as Gtk]))


(def datetime* (poll 1000 #(GLib/DateTime.new_now_local)))
(def date* (bind datetime* #(.format % "%m %d")))
(def time* (bind datetime* #(.format % "%H %M")))


(defn Datetime [_]
  #jsx [:menubutton {:name       :datetime
                     :cssClasses [:island]}
        [:box {:vertical true}
         [WrappedLabel {:label date*}]
         [Gtk/Separator {:orientation Gtk/Orientation.VERTICAL}]
         [WrappedLabel {:label time*}]]
        [:popover
         [Gtk/Calendar]]])
