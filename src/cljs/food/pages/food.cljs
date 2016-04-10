(ns food.pages.food
  (:require [reagent.session :as session]
            [food.ajax.food :as ajax]))

(defn food-group-list []
  (let [food-groups (session/get :food-groups)]
    (if (or (nil? food-groups) (empty? food-groups))
      (ajax/get-food-groups))
    [:div.container
     (for [food-group food-groups]
       ^{:key (:id food-group)}
       [:div.row
        [:div.col-md-12
         [:a {:href (str "#food/group/" (:id food-group))}
          [:h2 (:name food-group)]]]])]))

(defn food-page []
  (let [food-group-id (session/get :food-group-id)
        page-num (session/get :page-num)
        current-page (session/get (keyword (str "food-group-" food-group-id "-page-" page-num)))]
    (if (or (nil? current-page) (empty? current-page))
      (ajax/get-food-by-group food-group-id page-num))
    [:div.container
     (for [food current-page]
       ^{:key (:id food)}
       [:div.row
        [:div.col-md-12
         [:h2 (:long_desc food)]]])]))