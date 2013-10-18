(ns colliery.models.histories
  (:use korma.core
        korma.extensions
        [colliery.models.entities :only (histories history-versions)]))

(defn create [{user-id :id} title]
  (insert histories (values {:user user-id :title title})))

(defn update-history [hist k f & args]
  (update histories
          (set-fields (apply update-in (concat [hist [k] f] args)))
          (where hist)))
