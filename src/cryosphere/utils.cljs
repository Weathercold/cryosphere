(ns cryosphere.utils
  (:require ["astal" :refer [Binding derive] :as astal]))

#_{:clj-kondo/ignore [:redefined-var]}
(defn run! [f coll]
  (doall (map f coll)))
(def domap
  "Map over coll eagerly"
  run!)

(defn bind
  "Bind to emitter or its prop and optionally change transform fn"
  [emitter & prop-or-fn]
  (let [b (if (string? (first prop-or-fn))
            (astal/bind emitter (first prop-or-fn))
            (astal/bind emitter))]
    (if (fn? (last prop-or-fn))
      (.as b (last prop-or-fn))
      b)))

(defn derive-props
  "Binds to one or many props of emitter and derives from them"
  ([emitter props]
   (derive-props emitter props identity))
  ([emitter props f]
   (derive (doall (map #(astal/bind emitter %) props)) f)))

(defn as
  "Apply f to x, using .as() if x is a Binding"
  [f x]
  (if (instance? Binding x) (.as x f) (f x)))

(defn round
  "Round to n decimal places"
  [x n]
  (let [m (Math/pow 10 n)]
    (-> x (* m) Math/round (/ m))))
