(ns cryosphere.widgets.bar.system)


(defn System [_]
  #jsx [:box {:name       :system
              :cssClasses [:island]
              :vertical   true}
        [:label {:label "󱄅"}]])
