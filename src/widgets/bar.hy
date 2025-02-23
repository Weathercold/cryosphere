(import astal *)
(import astal.gtk3 *)


(setv *time (.poll (Variable "") 1000 "date"))


(defn Bar [monitor]
  (Widget.Window
   :namespace "bar"
   :name "bar"
   :visible True
   :anchor (| Astal.WindowAnchor.TOP Astal.WindowAnchor.LEFT Astal.WindowAnchor.RIGHT)
   :exclusivity Astal.Exclusivity.EXCLUSIVE
   :child
   (Widget.Box
    :name "root"
    :children
    [(Widget.Box
      :class-name "island"
      :halign Gtk.Align.START
      :child
      (Widget.Label :label "Welcome to AGS!"))
     (Widget.Box
      :class-name "island"
      :halign Gtk.Align.END
      :hexpand True
      :child
      (Widget.Label :label (*time)))])))
