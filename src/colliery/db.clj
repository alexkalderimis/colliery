(ns colliery.db
  (:use environ.core
        [korma.db :only (postgres defdb)]
        [lobos.core :only (migrate rollback)]
        [lobos.connectivity :only (with-connection)]
        clojure.tools.logging))

(def db-params
  (postgres
    {:db        (env :database-name)
     :user      (env :username)
     :password  (env :username)}))

(defn migrate-db [] (with-connection db-params (migrate)))
(defn rollback-db [] (with-connection db-params (rollback)))
  
(defdb db db-params)

