(defproject eradi "0.1.0-SNAPSHOT"
            :description "A simple wiki with Markdown."
            :url "http://github.com/winks/eradi"
            :dependencies [[org.clojure/clojure "1.2.1"]
                           [noir "1.2.2"]
                           [congomongo "0.1.9"]
                           [org.markdownj/markdownj "0.3.0-1.0.2b4"]]
            :profiles {:dev {:dependencies [[lein-marginalia "0.7.0"]]}}
            :min-lein-version "2.0.0"
            :main eradi.server)
