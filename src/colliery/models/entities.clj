(ns colliery.models.entities
  (:use korma.core
        korma.extensions
        colliery.models.util
        [colliery.db :only (db)]))

(declare users current-users histories history-versions)

(defentity users
  (database db) 
  (entity-fields :id :name)
  (prepare ensure-id)
  (prepare update-timestamps)
  (has-many histories {:fk :user}))

(defentity current-users
  (table (subselect users
                    (where {:deleted_at nil}))
  :current-users))

(defentity histories
  (database db)
  (entity-fields :title)
  (belongs-to users {:fk :user})
  (prepare ensure-id)
  (prepare update-timestamps)
  (prepare (versioned history-versions)))

(defentity history-versions
  (database db)
  (entity-fields :title)
  (belongs-to users {:fk :user}))
