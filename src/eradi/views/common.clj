(ns eradi.views.common
  (:require [noir.validation :as vali])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpartial layout [mytitle & content]
            (html5
              [:head
               [:title (str mytitle " | eradi")]
               (include-css "/css/reset.css")
               (include-css "/css/main.css")]
              [:body
               [:div#wrapper
                content
                [:div#footer
                  "["
                  (link-to "/" "home")
                  " | "
                  (link-to "/pages" "all pages")
                  " | "
                  (link-to (str "/add") "add page")
                  "]"]]]))

(defpartial editlink
  [page]
  [:div.edit
     "(last revision: "
     (int (:revision page))
     " by "
     (link-to (str "/user/" (:author page)) (:author page))
     ") "
     (link-to (str "/edit/" (:name page)) "edit")])

(defpartial wikipage
  [headline content]
  (layout
    headline
    [:h1 headline]
    content))

(defpartial wikicontent
  [page]
  [:div.content
   (:body page)]
   (editlink page))

(defpartial s404content
  []
  [:div.error
   [:span "Page not found!"]])

(defpartial wikiitem
  [item base]
  [:li
   (link-to (str "/" base "/" (:name item)) (:name item))])

(defpartial wikilist
  [items]
  [:ul.wikilist
   (let [num (count items)
         base (take num (cycle ["page"]))]
     (map wikiitem items base))])

(defn valid? [{:keys [name body author]}]
  (vali/rule (vali/min-length? name 1)
             [:name "Page name must be set"])
  (vali/rule (vali/has-value? body)
            [:body "Body must be set"])
  (vali/rule (vali/min-length? author 2)
            [:author "Author id must be at least 2 chars"])
  (not (vali/errors? :name :body :author)))

(defpartial error-item [[first-error]]
  [:p.error first-error])

(defpartial page-fields [{:keys [name body author]}]
  (vali/on-error :name error-item)
  (label "name" "Page name: ")
  (text-field "name" name)
  (vali/on-error :text error-item)
  (label "body" "Body: ")
  (text-area "body" body)
  (vali/on-error :author error-item)
  (label "author" "Author: ")
  (text-field "author" author))
