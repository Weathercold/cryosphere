(ns cryosphere.utils
  (:require ["astal" :refer [Binding Variable derive] :as astal]))

;;; region Misc

(def domap
  "Map over coll eagerly"
  run!)
(defmacro dofor
  "Eager list comprehension"
  [seq-exprs body]
  `(doall (for ~seq-exprs ~body)))

(defn round
  "Round to n decimal places"
  [x n]
  (let [m (Math/pow 10 n)]
    (-> x (* m) Math/round (/ m))))

;;; region Astal

(defn poll
  "Create new variable and poll with function"
  [interval f]
  (.poll (Variable (f)) interval f))

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
  "Bind to one or many props of emitter and derive from them"
  ([emitter props]
   (derive-props emitter props identity))
  ([emitter props f]
   (derive (doall (map #(astal/bind emitter %) props)) f)))

(defn as
  "Apply f to x, using .as() if x is a Binding"
  [f x]
  (if (instance? Binding x) (.as x f) (f x)))
