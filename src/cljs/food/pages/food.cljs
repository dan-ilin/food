(ns food.pages.food
  (:require [reagent.core :as r]
            [food.ajax.food :as ajax]))

(def food-group-data (r/atom {}))

(ajax/get-food-groups food-group-data)

(defn food-group-list []
  (let [food-groups @food-group-data]
    [:div.container
     (for [food-group food-groups]
       ^{:key (:id food-group)}
       [:div.row
        [:div.col-md-12
         [:a {:href (str "#food/group/" (:id food-group))}
          [:h2 (:name food-group)]]]])]))
