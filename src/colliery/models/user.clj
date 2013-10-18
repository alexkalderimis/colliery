(ns colliery.models.user
  (:require [clojure.string :as str])
  (:use korma.core
        korma.extensions
        [colliery.models.entities :only (users roles usersroles histories)]))

(def current-users (-> (select* users)
                       (where {:deleted_at nil})))

(defn all [] (select current-users))

(defn create [{:keys [id name]}]
  (insert users (values {:id id :name name})))

(defn fetch-by-id [uuid]
  (select-one current-users (where {:id uuid}) (with histories) (with roles)))

(defn fetch-user [user]
  (select-one current-users (where user) (with histories) (with roles)))

(defn delete-by-id [uuid]
  (update users
          (set-fields {:deleted_at (sqlfn now)})
          (where (= :id uuid))))

(defn with-role [{role-id :id role-name :name}]
  (cond
    role-id (select current-users
                    (where {:id [in (subselect usersroles
                                               (fields :users_id)
                                               (where {:roles_id role-id}))]}))
    role-name (select current-users
                      (where {:id [in (subselect usersroles
                                                 (fields :users_id)
                                                 (join roles (= :roles.id :usersroles.roles_id))
                                                 (where {:roles.name role-name}))]}))))

(defn add-role [{user-id :id} {role-name :name}]
  (let [add-role' #(insert usersroles (values {:users_id user-id :roles_id (:id %)}))
        role (or (select-one roles (where {:name role-name}))
                 (insert roles (values {:name role-name})))]
    (add-role' role)))
