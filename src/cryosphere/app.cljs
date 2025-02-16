(ns cryosphere.app
  (:require [cryosphere.widgets.bar :refer [Bar]]
            ["astal/gtk4" :refer [App]]
            ["../../assets/style.scss$default" :as style]))

(.start App {:css style
             :main (fn [] (seq (map Bar (App.get_monitors))))})
