(ns cryosphere.widgets.bar.hyprland
  (:require [cryosphere.widgets.vertical-label :refer [VLabel]]
            ["gi://AstalHyprland$default" :as AstalHyprland]))


(def *instance (AstalHyprland/get_default))
(def *workspace (.-focused-workspace *instance))


(defn Workspaces [_]
  #jsx [:box {:name "hyprland"
              :cssClasses ["island"]
              :vertical true}
        (doall (for [i (range 1 6)]
                 #jsx [:button {:onClicked #(.dispatch *instance "workspace" (str i))}
                       [VLabel {:label i}]]))])
