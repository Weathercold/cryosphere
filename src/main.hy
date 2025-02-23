(import astal *)
(import astal.gtk3 *)

(import widgets.bar :as bar)

(defn reload-css []
  (.reset-css App)
  (exec "sass ../assets/style.scss /tmp/cryosphere/style.css")
  (.apply-css App "/tmp/cryosphere/style.css"))

(.start App :main
        (fn []
          (.add-window App bar)))

(monitor-file "../assets" (fn [_, op] (when (= op 1)
                                        (print file op))))
