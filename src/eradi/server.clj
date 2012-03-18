;; default noir server.clj

(ns eradi.server
  (:require [noir.server :as server]))

;; display... stuff?
(server/load-views "src/eradi/views/")

;; hey, ho, let's go!
(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'eradi})))