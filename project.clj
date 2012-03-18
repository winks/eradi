(defproject eradi "0.1.0-SNAPSHOT"
            :description "A simple wiki"
            :dependencies [[org.clojure/clojure "1.2.1"]
                           [noir "1.2.2"]
                           [congomongo "0.1.8"]
                           [org.markdownj/markdownj "0.3.0-1.0.2b4"]]
            :dev-dependencies [[lein-marginalia "0.7.0"]]
            :main eradi.server)

