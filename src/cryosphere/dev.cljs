(ns cryosphere.dev
  (:require [cryosphere.app]
            ["astal" :refer [execAsync]]
            ["astal/gtk4" :refer [App]]
            ["gi://AstalIO$default" :as AstalIO]
            ["gi://Gio$default" :as Gio]))


(defn ^:async reload-css [_ ev]
  (when (= ev Gio/FileMonitorEvent.CHANGED)
    (App.reset_css)
    (js-await (execAsync "sass ../../assets/style.scss /tmp/cryosphere/style.css"))
    (App.apply_css "/tmp/cryosphere/style.css")
    (println "CSS reloaded")))

(defn -main []
  (AstalIO/monitor_file "../../assets" reload-css)) ; Hot reload


(-main)
