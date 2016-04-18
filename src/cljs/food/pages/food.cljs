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
        [:div.col-md-10
         [:a {:href (str "#food/group/" (:id food-group))}
          [:h2 (:name food-group)]]]
        [:div.col-md-2
         [:p (str "(" (:food_count food-group) " total)")]]])]))

(defn food-page []
  (let [food-group-id (session/get :food-group-id)
        page-num (session/get :page-num)
        current-page (session/get (keyword (str "food-group-" food-group-id "-page-" page-num)))]
    (if (nil? current-page)
      (ajax/get-food-by-group food-group-id page-num))
    [:div.container
     (for [food current-page]
       ^{:key (:id food)}
       [:div.row
        [:div.col-md-12
         [:a {:href (str "#food/" (:id food) "/nutrition")}
          [:h2 (:long_desc food)]]]])
     [:div.row
      [:nav [:ul.pager
             (if (<= 1 page-num)
               [:li.disabled [:a {:href "#"} "Prev"]]
               [:li [:a {:href (str "#food/group/" food-group-id "?page=" (dec page-num))} "Prev"]])
             [:li [:a {:href (str "#food/group/" food-group-id "?page=" (inc page-num))} "Next"]]]]]]))

(defn nutrition-page []
  (let [food-id (session/get :food-id)
        nutrition-info (session/get (keyword (str "food-" food-id "-nutrition")))]
    (if (nil? nutrition-info)
      (ajax/get-nutrition-by-food food-id))
    [:div.container
     (for [nutrient nutrition-info]
       ^{:key (:id nutrient)}
       [:div.row
        [:div.col-md-8
         [:h3 (:name nutrient)]]
        [:div.col-md-4
         [:p (str (:amount nutrient) (:units nutrient))]]])]))