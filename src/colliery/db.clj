(ns colliery.db
  (:use colliery.config
        [korma.db :only (postgres defdb)]
        [lobos.core :only (migrate rollback)]
        [lobos.connectivity :only (with-connection)]
        clojure.tools.logging))

(defn db-params []
  (let [{db :database-name user :database-user password :database-pass} *env*]
    (postgres {:db db :user user :password password})))

(defn migrate-db [] (with-connection (db-params) (migrate)))
(defn rollback-db [& args] (with-connection (db-params) (apply rollback args)))
  
(defdb db (db-params))

