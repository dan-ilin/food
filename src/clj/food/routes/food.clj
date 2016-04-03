(ns food.routes.food
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.coercions :refer [as-int]]
            [food.db.core :as db]))

(defroutes food-routes
           (GET "/food/group" [] (db/get-food-groups))
           (GET "/food/group/:id" [id :<< as-int offset :<< as-int limit :<< as-int]
             (db/get-food-by-food-group {:food_group_id id
                                         :offset        offset
                                         :limit         limit}))
           (GET "/food/:id/nutrition" [id :<< as-int]
             (db/get-nutrition-by-food {:food_id id}))
           (GET "/nutrients" [] (db/get-nutrients))
           (GET "/nutrients/common" [] (db/get-common-nutrients)))