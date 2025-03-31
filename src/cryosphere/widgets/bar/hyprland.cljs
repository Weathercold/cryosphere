(ns cryosphere.widgets.bar.hyprland
  (:require [cryosphere.utils :refer [bind]]
            ["gi://AstalHyprland$default" :as AstalHyprland]
            ["gi://Gdk?version=4.0$default" :as Gdk]))


(def hyprland (AstalHyprland/get_default))


(defn Workspaces [_]
  #jsx [:box {:name       :workspaces
              :cssClasses [:island]
              :vertical   true}
        (doall (for [i (range 1 6)]
                 #jsx [:button {:onClicked #(.dispatch hyprland :workspace (str i))
                                :cursor    (Gdk/Cursor.new_from_name :pointer nil)}
                       [:label {:label  (bind hyprland :focused-workspace
                                              #(if (= i (.-id %)) "" ""))
                                :xalign 0.59}]]))])
