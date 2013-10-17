(ns colliery.controllers.lumps
  (:use [compojure.core :only (defroutes GET POST)]
        [clojure.tools.logging])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [colliery.views.lumps :as view]
            [colliery.models.lumps :as model]))

(defn index []
  (view/index (model/all)))

(defn create [lump]
  (info "New lump?" lump)
  (when-not (str/blank? lump)
    (model/create lump))
  (ring/redirect "/"))

(defroutes routes
  (GET "/" [] (index))
  (POST "/" [lump] (create lump)))
