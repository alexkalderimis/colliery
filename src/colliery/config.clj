(ns colliery.config
  (:use environ.core))

(def ^:dynamic *env* env)

(defmacro with-environment [e & body]
  `(binding [*env* ~e] ~@body))
