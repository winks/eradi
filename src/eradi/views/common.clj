;; Make stuff pretty.

(ns eradi.views.common
  (:require [noir.validation :as vali]
            [eradi.tools :as tools])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

;; eradi's base layout
(defpartial layout [mytitle & content]
  (html5
    [:head
     [:title (str mytitle " | eradi")]
     (include-css "/css/bootstrap.css")
     (include-css "/css/bootstrap2.css")]
    [:body
     [:div.container
      content
      [:div#footer.span10.offset1.btn-group
       (link-to {:class "btn"} "/" "home")
       (link-to {:class "btn"} "/pages" "all pages")
       (link-to {:class "btn"} (str "/add") "add page")]]]))

;; display revision and last author
(defpartial editlink
  [page]
  [:div.span10.offset1.edit
   "(last revision: "
   (int (:revision page))
   " by "
   (link-to (str "/user/" (:author page)) (:author page))
   ") "
   (link-to (str "/edit/" (:name page)) "edit")])

;; display a wiki page
(defpartial wikipage
  [headline content]
  (layout
    headline
    [:div#header.span10.offset1
     [:h1 headline]]
    [:div.span10.offset1.content
     content]))

;; display central content of a wiki page
(defpartial wikicontent
  [page]
  (tools/md (:body page))
  (editlink page))

;; display a 404
(defpartial s404content
  []
  [:span "Page not found!"])

;; a list item
(defpartial wikiitem
  [item base]
  [:li
   (link-to (str "/" base "/" (:name item)) (:name item))
   " (revision: "
   (int (:revision item))
   ")"])

;; a list of items
(defpartial wikilist
  [items]
  [:ul.wikilist
   (let [num (count items)
         base (take num (cycle ["page"]))]
     (map wikiitem items base))])

;; validate a page submission (create/update)
(defn valid? [{:keys [name body author]}]
  (vali/rule (vali/min-length? name 1)
             [:name "Page name must be set"])
  (vali/rule (vali/has-value? body)
             [:body "Body must be set"])
  (vali/rule (vali/min-length? author 2)
             [:author "Author id must be at least 2 chars"])
  (not (vali/errors? :name :body :author)))

;; display an error message
(defpartial error-item [[first-error]]
  [:p.error first-error])

;; show a form for pages
(defpartial page-fields [{:keys [name body author revision]}]
  [:fieldset
   [:div.control-group
    (vali/on-error :name error-item)
    (label {:class "control-label"} "name" "Page name: ")
    [:div.controls
     (text-field "name" name)]]
   [:div.control-group
    (vali/on-error :body error-item)
    (label {:class "control-label"} "body" "Body: ")
    [:div.controls
     (text-area "body" body)]]
   [:div.control-group
    (vali/on-error :author error-item)
    (label {:class "control-label"} "author" "Author: ")
    [:div.controls
     (text-field "author" author)]]
   (hidden-field "revision" (tools/fnord-int revision))])
