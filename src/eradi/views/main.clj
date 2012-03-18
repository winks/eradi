(ns eradi.views.main
  (:require [eradi.views.common :as common]
            [eradi.models.pages :as empages]
            [noir.response :as resp])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpage "/" []
  (common/wikipage
    "Welcome to eradi"
    [:div.content "a simple wiki"]))

(defpage [:post "/add"] {:as page}
  (if (common/valid? page)
    (do
      (empages/save page)
      (render (str "/page/" (:name page)))
    (render "/add" page))))

(defpage "/add" {:as page}
  (common/wikipage
    "Add a page"
    [:div.content
     (form-to [:post "/add"]
              (common/page-fields page)
              (submit-button "Add page"))]))

(defpage [:post "/edit/:pgname"] {:keys [pgname] :as post}
  (if (empages/updatepage post)
    (resp/redirect (str "/edit/" pgname))
    (render "/edit/:pgname" post)))

(defpage "/edit/:pgname" {:keys [pgname]}
  (if-let [post (empages/getpage pgname)]
    (common/wikipage
      (html "Edit page: " (link-to (str "/page/" pgname) pgname))
      [:div.content
       (form-to [:post (str "/edit/" pgname)]
                (common/page-fields post)
                (submit-button "Edit page"))])))

(defpage "/page/:pgname" {:keys [pgname]}
  (let [page (empages/getpage pgname)]
    (if (nil? page)
      (common/wikipage
        (h pgname)
        (common/s404content))
      (common/wikipage
        (:name page)
        (common/wikicontent page)))))

(defpage "/test" []
  (common/layout
    [:p (:author (empages/gettestpage))]))

(defpage "/pages" []
  (common/wikipage
    "All pages"
    (common/wikilist
      (empages/getpages))))