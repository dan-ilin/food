(ns food.pages.main
  (:require [reagent.session :as session]
            [markdown.core :refer [md->html]]))

(defn home-page []
  [:div.container
   [:div.jumbotron
    [:h1 "Welcome to food"]
    [:p "Time to start building your site!"]
    [:p [:a.btn.btn-primary.btn-lg {:href "http://luminusweb.net"} "Learn more »"]]]
   [:div.row
    [:div.col-md-12
     [:h2 "Welcome to ClojureScript"]]]
   (when-let [docs (session/get :docs)]
     [:div.row
      [:div.col-md-12
       [:div {:dangerouslySetInnerHTML
              {:__html (md->html docs)}}]]])])

(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     "this is the story of food... work in progress"]]])