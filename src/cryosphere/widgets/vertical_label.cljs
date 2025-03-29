(ns cryosphere.widgets.vertical-label
  (:require [cryosphere.utils :refer [as]]
            ["gi://Gtk?version=4.0$default" :as Gtk]
            ["gi://Pango$default" :as Pango]))


(defn VLabel [{:keys [label]}]
  #jsx [:label {:label (as #(str "<span insert-hyphens='no'>" % "</span>") label)
                :use-markup true
                :wrap true
                :wrap-mode Pango/WrapMode.WORD_CHAR
                :max-width-chars 3
                :justify Gtk/Justification.CENTER}])
