;; rudimentary mongodb connectivity

(ns eradi.models.base
  (:require [somnium.congomongo :as mongo]))

;; connect to a local mongodb instance
(def conn
  (mongo/make-connection "mydb"
                         :host "127.0.0.1"
                         :port 27017))

; (mongo/set-connection! conn)