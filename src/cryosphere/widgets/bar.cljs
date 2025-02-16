(ns cryosphere.widgets.bar
  (:require ["astal" :refer [Variable]]
            ["astal/gtk4" :refer [App Astal Gtk]]))

(def *time (.poll (Variable "") 1000 "date"))

(defn Bar [monitor]
  (let [{:keys [TOP LEFT RIGHT]} Astal.WindowAnchor]
    #jsx [:window {:visible true
                   :cssClasses ["Bar"]
                   :gdkmonitor monitor
                   :exclusivity Astal.Exclusivity.EXCLUSIVE
                   :anchor (| TOP LEFT RIGHT)
                   :application App}
          [:centerbox {:cssName "centerbox"}
           [:button {:onClicked "echo hello"
                     :hexpand true
                     :halign Gtk.Align.CENTER}
            "Welcome to AGS!"]
           [:box]
           [:menubutton {:hexpand true
                         :halign Gtk.Align.CENTER}
            [:label {:label (*time)}]
            [:popover
             [Gtk.Calendar]]]]]))
