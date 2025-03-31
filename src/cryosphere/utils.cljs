(ns cryosphere.utils
  (:require ["astal" :refer [Binding Variable derive] :as astal]))

;;; region Misc

(defn domap
  "Map over coll eagerly"
  [f & colls]
  (doall (apply map f colls)))
(defmacro dofor
  "Eager list comprehension"
  [seq-exprs body]
  `(doall (for ~seq-exprs ~body)))

(defn round
  "Round to n decimal places"
  [x n]
  (let [m (Math/pow 10 n)]
    (-> x (* m) Math/round (/ m))))

#_{:clj-kondo/ignore [:redefined-var]}
(defn reverse [s]
  (apply str (clojure.core/reverse s)))

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

(defn derive-vprops
  "Bind to one or many props of value of dep and derive from them. Rederive on
   change."
  [dep props f]
  (let [derived  (Variable)
        rederive (fn [] (when-let [v (.get dep)]
                          (let [binds   (domap #(astal/bind v %) props)
                                compute (fn [] (apply f (domap #(.get %) binds)))
                                unsubs  (domap (fn [b] (.subscribe b #(.set derived (compute))))
                                               binds)]
                            (.set derived (compute))
                            (.onDropped derived (fn [] (run! #(%) unsubs))))))
        unsub-dep (.subscribe dep rederive)]
    (rederive)
    (.onDropped derived unsub-dep)
    derived))

(defn derive-props
  "Bind to one or many props of dep and derive from them"
  ([dep props]
   (derive-props dep props identity))
  ([dep props f]
   (derive (domap #(astal/bind dep %) props) f)))

(defn as
  "Return a new binding with f as transform function if x is a Binding, else
   apply f to x."
  [f x]
  (if (instance? Binding x) (.as x f) (f x)))
