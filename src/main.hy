(import astal *)
(import astal.gtk3 *)

(import HyREPL.server :as repl)

(import .widgets.bar [Bar])


(defn reload-css []
  (.reset-css App)
  (exec "sass assets/style.scss /tmp/cryosphere/style.css")
  (.apply-css App "/tmp/cryosphere/style.css")
  (print "CSS reloaded"))

(.start App :main
        (fn [] (list (map Bar (.get_monitors App)))))

(monitor-file "assets" (fn [_, op] (when (= op 1) (reload-css))))


(defmain [&rest _]
  (let [s (repl.start-server)]
    (.start App
            :main (fn [] (list (map Bar (.get_monitors App))))
            :css "assets/style.scss")

    (monitor-file "assets"
                  (fn [_ op] (when (= op 1) (reload-css))))))
