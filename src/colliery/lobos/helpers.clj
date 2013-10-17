(ns colliery.lobos.helpers
  (:refer-clojure :exclude [bigint boolean char double float time])
  (:use (lobos schema)))

(def-simple-typed-columns uuid)

(defn uuid-key [table]
  (uuid table :id :primary-key :not-null))

(defn timestamps [table]
  (-> table
      (timestamp :updated_at)
      (timestamp :deleted_at)
      (timestamp :created_at (default (now)))))

(defn belongs-to
  ([table ptable]
   (belongs-to table ptable (-> ptable name (str "_id") keyword)))
  ([table ptable cname]
    (uuid table cname [:refer ptable :id :on-delete :cascade])))

(defn indirection-tbl [table1 table2]
  (let [tname (keyword (str (name table1) (name table2)))
        index-all-keys #(index % (keys (:columns %)) :unique)]
    (-> (table tname)
        (belongs-to table1)
        (belongs-to table2)
        index-all-keys)))

;; Default table, with a uuid id key and timestamps for creation, last update and deletion
(defmacro tbl [name & elements]
  `(-> (table ~name)
       (timestamps)
       ~@(reverse elements)
       (uuid-key)))

