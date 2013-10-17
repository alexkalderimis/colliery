(ns colliery.models.histories
  (:use korma.core
        korma.extensions
        [colliery.models.entities :only (histories history-versions)]))

(defn create [{user-id :id} title]
  (insert histories (values {:user user-id :title title})))
