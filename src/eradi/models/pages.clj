(ns eradi.models.pages
  (:require [somnium.congomongo :as mongo])
  (:use eradi.models.base))

(defn save
  [page]
  (let [{:keys [name body author]} page]
    (mongo/with-mongo conn
      (mongo/insert!
        :pages
        {:author author,
         :name name,
         :body body,
         :revision 1}))))

(defn updatepage
  [data]
  (if-let [name (:name data)]
    (if-let [post (getpage name)]
      (mongo/with-mongo
        conn (mongo/update! :pages
                            post
                            (merge post { :body (:body data)}))))))


(defn gettestpage
  []
  (mongo/with-mongo conn
                    (mongo/fetch-one :pages)))

(defn getpage
  [name]
  (mongo/with-mongo conn
                    (mongo/fetch-one :pages
                                     :where {:name name})))

(defn getpages
  []
  (mongo/with-mongo conn
                    (mongo/fetch :pages)))