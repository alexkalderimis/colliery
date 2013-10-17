(ns colliery.core
  (:use [compojure.core :only (defroutes)]
        [ring.adapter.jetty :as ring]
        [clojure.tools.logging])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [colliery.util :as util]
            [colliery.views.layout :as layout])
  )

(defroutes coalface
  (route/resources "/")
  (route/not-found (layout/four-oh-four)))

(def colliery (handler/site coalface))

(defn start [port] 
    (run-jetty #'colliery {:port port :join? false}))

(defn -main [] 
  (let [port (util/get-port :PORT)]
    (start port)))
        
