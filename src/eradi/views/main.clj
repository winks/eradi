;; All views for eradi

(ns eradi.views.main
  (:require [eradi.views.common :as common]
            [eradi.models.pages :as pages]
            [noir.response :as resp])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

;; index page
(defpage "/" []
  (common/wikipage
    "Welcome to eradi"
    [:div.content "a simple wiki"]))

;; POST target of page adding
(defpage [:post "/add"] {:as page}
  (if (common/valid? page)
    (do
      (pages/save page)
      (render (str "/page/" (:name page)))
    (render "/add" page))))

;; "add a page" form
(defpage "/add" {:as page}
  (common/wikipage
    "Add a page"
    [:div.content
     (form-to [:post "/add"]
              (common/page-fields page)
              (submit-button "Add page"))]))

;; POST target of page editing
(defpage [:post "/edit/:pgname"] {:keys [pgname] :as post}
  (if (pages/update post)
    (resp/redirect (str "/edit/" pgname))
    (render "/edit/:pgname" post)))

;; "edit page" form
(defpage "/edit/:pgname" {:keys [pgname]}
  (if-let [post (pages/get-one pgname)]
    (common/wikipage
      (html "Edit page: " (link-to (str "/page/" pgname) pgname))
      [:div.content
       (form-to [:post (str "/edit/" pgname)]
                (common/page-fields post)
                (submit-button "Edit page"))])))

;; display a page
(defpage "/page/:pgname" {:keys [pgname]}
  (let [page (pages/get-one pgname)]
    (if (nil? page)
      (common/wikipage
        (h pgname)
        (common/s404content))
      (common/wikipage
        (:name page)
        (common/wikicontent page)))))

;; just a test page
(defpage "/test" []
  (common/layout
    [:p (:author (pages/get-test))]))

;; list of all pages
(defpage "/pages" []
  (common/wikipage
    "All pages"
    (common/wikilist
      (pages/get-all))))