(ns cryosphere.widgets.labels
  (:require [cryosphere.utils :refer [as] :as utils]
            ["gi://Gtk?version=4.0$default" :as Gtk]
            ["gi://Pango$default" :as Pango]))


(defn WrappedLabel
  "A label that wraps text at a specified width"
  [{:keys [attributes] :as opts}]
  (let [opts'       (dissoc opts :attributes)
        attributes' (doto (if attributes (.copy attributes) (Pango/AttrList.new))
                      (.change (Pango/attr_insert_hyphens_new false)))]
    #jsx [:label {:max-width-chars 3
                  :wrap            true
                  :wrap-mode       Pango/WrapMode.CHAR
                  :attributes      attributes'
                  :justify         Gtk/Justification.CENTER
                  :&               opts'}]))

;; Rant
;; Why is it so hard to do something as basic as rotating text in GTK 4.0? In
;; GTK 3.0 you could just set the angle property, but GTK 4.0 removed that
;; property and provided no alternatives. They claimed you could use css rotate,
;; but that doesn't change the widget size, and to do that you'd have to
;; implement a subclass of label with custom measure() and allocate() (see
;; https://discourse.gnome.org/t/rotate-a-label-in-gtk4/11118). What an
;; excellent DX.
(defn RotatedLabel
  "A pseudo-vertical WrappedLabel with a max width of 1 and a horizontal gravity"
  [{:keys [label gravity attributes] :or {gravity Pango/Gravity.EAST} :as opts}]
  (let [opts' (dissoc opts :label :gravity :attributes)
        attributes' (doto (if attributes (.copy attributes) (Pango/AttrList.new))
                      (.insert (Pango/attr_gravity_new gravity))
                      (.insert (Pango/attr_gravity_hint_new Pango/GravityHint.STRONG))
                      (.insert (Pango/attr_line_height_new 0.5)))]
    #jsx [WrappedLabel {:label           (as utils/reverse label)
                        :max-width-chars 1
                        :attributes      attributes'
                        :&               opts'}]))
