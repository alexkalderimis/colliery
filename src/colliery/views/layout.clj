(ns colliery.views.layout
  (:use [hiccup.page :only (html5 include-css)]))

(defn common [title & body]
  (html5
    [:head
      [:meta {:charset "utf-8"}]
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1, maximum-scale=1"}]
      [:title title]
      (include-css "/style/base.css" "/style/skeleton.css" "/style/screen.css")
      (include-css "http://fonts.googleapis.com/css?family=Sigmar+One&v1") ]
    [:body
      [:section {:id "header"}
       [:h1 {:class "container"} "Colliery"]]
      [:section {:id "content" :class "container"} body]]
    ))

(defn four-oh-four []
  (common "Page not found"
    [:div {:id "four-oh-four"} "The page you requested could not be found"]))

