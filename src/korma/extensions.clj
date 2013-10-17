(ns korma.extensions
  (:use [korma.core :only (select select* limit)]))

(defn to-alias [f] (if (coll? f) (last f) f))

(defmacro select-one [& forms] `(first (select ~@forms (limit 1))))

(defmacro select-values [tname & forms]
  `(let [query# (-> ~tname select* ~@forms)
         [f#] (map to-alias (:fields query#))]
     (map f# (select query#))))

