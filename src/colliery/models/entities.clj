(ns colliery.models.entities
  (:use korma.core
        korma.extensions
        colliery.models.util
        [colliery.db :only (db)]))

(declare users current-users histories history-versions intermine-accounts roles)

(defentity users
  (database db) 
  (entity-fields :id :name)
  (prepare ensure-id)
  (prepare update-timestamps)
  (has-many histories {:fk :user})
  (many-to-many roles :usersroles))

(defentity histories
  (database db)
  (entity-fields :title)
  (belongs-to users {:fk :user})
  (prepare ensure-id)
  (prepare update-timestamps)
  (prepare (versioned histories history-versions)))

(defentity history-versions
  (database db)
  (entity-fields :title)
  (belongs-to users {:fk :user}))

(defentity roles
  (database db)
  (prepare ensure-id)
  (entity-fields :name))

(defentity usersroles
  (database db)
  (entity-fields :users_id :roles_id)
  (belongs-to users {:fk :users_id})
  (belongs-to roles {:fk :roles_id}))
