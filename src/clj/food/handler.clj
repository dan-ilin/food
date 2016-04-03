(ns food.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [food.layout :refer [error-page]]
            [food.routes.home :refer [home-routes]]
            [food.routes.food :refer [food-routes]]
            [compojure.route :as route]
            [food.middleware :as middleware]))

(def app-routes
  (routes
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (wrap-routes #'food-routes middleware/wrap-csrf)
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(def app (middleware/wrap-base #'app-routes))
