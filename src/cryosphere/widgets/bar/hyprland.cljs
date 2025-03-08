(ns cryosphere.widgets.bar.hyprland
  (:require [cryosphere.utils :refer [bind]]
            [cryosphere.widgets.vertical-label :refer [VLabel]]
            ["gi://AstalHyprland$default" :as AstalHyprland]))


(def hyprland (AstalHyprland/get_default))


(defn Workspaces [_]
  #jsx [:box {:name "hyprland"
              :cssClasses ["island"]
              :vertical true}
        (doall (for [i (range 1 6)]
                 #jsx [:button {:onClicked #(.dispatch hyprland "workspace" (str i))}
                       [VLabel {:label (bind hyprland "focused-workspace"
                                             #(if (= i (.-id %)) "●" "○"))}]]))])
