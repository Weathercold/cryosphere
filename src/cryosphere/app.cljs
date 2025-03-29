(ns cryosphere.app
  (:require [cryosphere.widgets.bar :refer [Bar]]
            ["../../assets/style.scss$default" :as style]
            ["astal/gtk4" :refer [App]]))


(defn -main []
  (App.start {:css style
              :main (fn [] (run! Bar (App.get_monitors)))}))


(-main)
