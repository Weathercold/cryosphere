(ns cryosphere.widgets.bar
  (:require [cryosphere.widgets.vertical-label :refer [VLabel]]
            ["astal" :refer [Variable bind]]
            ["astal/gtk4" :refer [App Astal Gtk]]))


(def *time (.poll (Variable "") 1000 "date"))


(defn Bar [monitor]
  (let [{:keys [LEFT TOP BOTTOM]} Astal.WindowAnchor]
    #jsx [:window {:visible true
                   :cssClasses ["Bar"]
                   :gdkmonitor monitor
                   :exclusivity Astal.Exclusivity.EXCLUSIVE
                   :anchor (| LEFT TOP BOTTOM)
                   :application App}
          [:box {:cssName "root"
                 :vertical true}
           [:box {:cssName "island"
                  :vertical true
                  :valign Gtk.Align.START}
            [:button {:onClicked "echo hello"
                      :vexpand true}
             [VLabel {:label "Hello world"}]]]
           [:box {:cssName "island"
                  :vertical true
                  :valign Gtk.Align.END}
            [:menubutton {:vexpand true}
             [VLabel {:label (bind *time)}]
             [:popover
              [Gtk.Calendar]]]]]]))
