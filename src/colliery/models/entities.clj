(ns colliery.models.entities
  (:require colliery.db)
  (:use korma.core
        korma.extensions
        colliery.models.util))

(declare users histories history-versions intermine-accounts roles)

(defentity users
  (entity-fields :id :name)
  (prepare ensure-id)
  (prepare update-timestamps)
  (has-many histories {:fk :user})
  (many-to-many roles :usersroles))

(defentity histories
  (entity-fields :title)
  (belongs-to users {:fk :user})
  (prepare ensure-id)
  (prepare update-timestamps)
  (prepare (versioned histories history-versions)))

(defentity history-versions
  (entity-fields :title)
  (belongs-to users {:fk :user}))

(defentity roles
  (prepare ensure-id)
  (entity-fields :name))

(defentity usersroles
  (entity-fields :users_id :roles_id)
  (belongs-to users {:fk :users_id})
  (belongs-to roles {:fk :roles_id}))
