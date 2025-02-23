(import astal *)
(import astal.gtk3 *)


(defn watch-style []
  (lfor file (glob "./style/**" :recursive True)
        (when (not (in ".css" file)) (monitor-file file (fn [_, op] (when (= op 1) (print file op) (exec "sass ../assets/style.scss /tmp/cryosphere/style.css") (.apply-css App "/tmp/cryosphere/style.css")))))))

(.start App :main (fn [] ()))
