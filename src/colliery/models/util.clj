(ns colliery.models.util
  (:use
    [korma.core :only (sqlfn where insert values)]
    korma.extensions))

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
  [originals versions & [pks] ]
  (fn [{:keys [revision id deleted_at] :as input}]
    (let [pks (if (empty? pks) [:id] pks)]
      (cond
          (and revision id (not deleted_at)) (do 
              (insert versions (values (select-one originals (where (select-keys input pks)))))
              (update-in input [:revision] inc))
          :else input))))

