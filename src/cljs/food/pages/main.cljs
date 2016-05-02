(ns food.pages.main
  (:require [reagent.session :as session]
            [markdown.core :refer [md->html]]
            [reagent.core :as r]))

(defn nav-link [uri title page collapsed?]
  [:li.nav-item
   {:class (when (= page (session/get :page)) "active")}
   [:a.nav-link
    {:href     uri
     :on-click #(reset! collapsed? true)} title]])

(defn navbar []
  (let [collapsed? (r/atom true)]
    (fn []
      [:nav.navbar.navbar-light.bg-faded
       [:button.navbar-toggler.hidden-sm-up
        {:on-click #(swap! collapsed? not)} "☰"]
       [:div.collapse.navbar-toggleable-xs
        (when-not @collapsed? {:class "in"})
        [:a.navbar-brand {:href "#/"} "food"]
        [:ul.nav.navbar-nav
         [nav-link "#/" "Home" :home collapsed?]
         [nav-link "#/food/groups" "Food Groups" :food-groups collapsed?]]]])))

(defn home-page []
  [:div.container
   [:div.jumbotron
    [:h1 "Nutrition Explorer"]
    [:p "Use this web app to explore nutrition data for all of your favorite foods, broken down by food group."]
    [:p [:a.btn.btn-primary.btn-lg {:href "#/food/groups"} "Click here to start »"]]]])