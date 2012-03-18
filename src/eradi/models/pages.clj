;; models. for pages. yes.

(ns eradi.models.pages
  (:require [somnium.congomongo :as mongo])
  (:use eradi.models.base))

;; return a test page
(defn get-test
  []
  (mongo/with-mongo conn
                    (mongo/fetch-one :pages)))

;; return a page specified by name or name and revision
(defn get-one
  ([name]
    (mongo/with-mongo conn
                      (mongo/fetch-one :pages
                                       :where {:name name})))
  ([name revision]
    (mongo/with-mongo conn
                      (mongo/fetch-one :pages
                                       :where {:name name,
                                               :revision revision}))))

;; return all pages
(defn get-all
  []
  (mongo/with-mongo conn
                    (mongo/fetch :pages)))

;; save a new page
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

;; update an existing page
(defn update
  [data]
  (if-let [name (:name data)]
    (if-let [post (get-one name)]
      (mongo/with-mongo
        conn (mongo/update! :pages
                            post
                            (merge post { :body (:body data)}))))))