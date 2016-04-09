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
