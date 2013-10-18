(ns colliery.models.roles
  (:use korma.core
        korma.extensions
        [colliery.models.entities :only (roles usersroles)]))

(defn fetch [role]
  (select-one roles (where (select-keys role [:id :name]))))

(defn all []
  (select roles
          (join usersroles (= :usersroles.roles_id :id))
          (fields :name :id)
          (group :name)
          (group :id)
          (group :usersroles.roles_id)
          (aggregate (count :usersroles.users_id) :n-users-in-role)))
