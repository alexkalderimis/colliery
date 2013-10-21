(ns colliery.test.db
  (:use environ.core
        [korma.db :only (defdb)]
        [korma.core :only (delete)]
        colliery.config))

(def test-pref #"^test-")

(defn grep-keys [f m] (select-keys m (filter f (keys m))))

(defn use-test-settings [[k v]]
  (let [kn (name k)
        k' (keyword (clojure.string/replace kn test-pref ""))]
    [k' v]))

(def settings
  (into env
        (map use-test-settings (grep-keys #(re-find test-pref (name %)) env))))

(binding [*env* settings]
  (require 'colliery.db))

(defn setup-db [t]
  (binding [*env* settings]
    (colliery.db/migrate-db)
    (t)))

(defn teardown-db [t]
  (binding [*env* settings]
    (t)
    (colliery.db/rollback-db :all)))

(defn remove-ents [& ents]
  (fn [f]
    (f)
    (doseq [e ents] (delete e))))
