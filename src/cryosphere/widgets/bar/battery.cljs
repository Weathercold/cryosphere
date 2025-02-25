(ns cryosphere.widgets.bar.battery
  (:require [cryosphere.widgets.vertical-label :refer [VLabel]]
            ["astal" :refer [Variable]]))
            ;; ["gi://AstalBattery$default" :as AstalBattery]


(def *battery (.poll (Variable "") 10000
                    ;;  #(.-percentage (AstalBattery/get_default))
                     "cat /sys/class/power_supply/BAT0/capacity"))

(defn Battery [_]
  #jsx [:box {:name "battery"
              :cssClasses ["island"]}
        [:circularprogress {:cssName "circ-prog"
                            :percentage (*battery #(/ (parseInt %) 100))
                            :line-width 3
                            :hexpand true}
         [VLabel {:label ""
                  :cssName "icon"}]]])
