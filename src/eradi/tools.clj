;; misc. tools for eradi

(ns eradi.tools
  (:import [com.petebevin.markdown MarkdownProcessor]))

;; The following functions handle preparation of doc text (both comment and docstring
;; based) for display through html & css.
;; Nicked from [marginalia](https://github.com/fogus/marginalia)

;; Markdown processor.
(def mdp (com.petebevin.markdown.MarkdownProcessor.))

(defn md
  "Markdown string to html converter. Translates strings like:

   \"# header!\" -> `\"<h1>header!</h1>\"`

   \"## header!\" -> `\"<h2>header!</h2>\"`

   ..."
  [s]
  (.markdown mdp s))

(defn fnord-int
  "Mongo or congomongo seem to sometimes return floats.

  And then sometimes the revision is just not set.
  "
  [x]
  (if (integer? x)
    x
    (if (nil? x)
      0
      (Integer/parseInt x))))
