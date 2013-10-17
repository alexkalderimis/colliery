(ns colliery.models.user
  (:require [clojure.string :as str])
  (:use korma.core
        korma.extensions
        [colliery.models.entities :only (users current-users)]))

(defn all [] (select current-users))

(defn create [{:keys [id name]}]
  (insert users (values {:id id :name name})))

(defn fetch-by-id [uuid]
  (select-one current-users (where {:id uuid})))

(defn delete-by-id [uuid]
  (update users
          (set-fields {:deleted_at (sqlfn now)})
          (where (= :id uuid))))
