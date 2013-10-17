(ns colliery.lobos.config
  (:require colliery.db)
  (:use lobos.connectivity))

(open-global (colliery.db/get-db-params))

