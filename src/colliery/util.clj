(ns colliery.util
  (:import (java.net ServerSocket)))

(defn get-port [env-var]
  (if-let [user-port (-> env-var str System/getenv)]
    (Integer/valueOf user-port)
    (let [sock     (ServerSocket. 0)
          sys-port (.getLocalPort sock)]
    (.close sock)
    sys-port)))
