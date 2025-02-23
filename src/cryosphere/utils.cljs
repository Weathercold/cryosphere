(ns cryosphere.utils
  (:require ["astal" :refer [Binding]]))

#_{:clj-kondo/ignore [:redefined-var]}
(defn run! [f coll]
  (doall (map f coll)))

(defn as
  "Apply f to x, using .as() if x is a Binding"
  [f x]
  (if (instance? Binding x) (.as x f) (f x)))
