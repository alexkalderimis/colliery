(ns colliery.models.util
  (:use [korma.core :only (sqlfn insert values)]))

(defn ensure-id
  "Make sure the model has an id"
  [{id :id :as input}]
  (if (nil? id) (assoc input :id (java.util.UUID/randomUUID)) input))

(defn update-timestamps
  "Make sure the timestamps are set correctly"
  [{:keys [updated_at deleted_at] :as input}]
  (if (not (some updated_at deleted_at))
    (assoc input :updated_at (sqlfn now))
    input))

(defn versioned
  "Make sure that the version is updated"
  [version-table]
  (fn [{:keys [revision id deleted_at] :as input}]
    (let [bumped (if (and revision id (not deleted_at))
                   (update-in input [:revision] inc)
                   input)]
      (insert version-table (values input))
      bumped)))

