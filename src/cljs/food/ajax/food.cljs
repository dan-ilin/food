(ns food.ajax.food
  (:require [ajax.core :refer [GET POST]]
            [reagent.session :as session]))

(def page-size 50)
(defn get-page-params [page-num]
  {:limit  page-size
   :offset (* page-size page-num)})

(defn handler [key response]
  (.log js/console (str response))
  (session/put! key response))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "Error: " status " " status-text)))

(defn get-food-groups []
  (GET "/api/food/group" {:handler       (partial handler :food-groups)
                          :error-handler error-handler}))

(defn get-food-by-group [id page-num]
  (GET (str "/api/food/group/" id) {:handler       (partial handler (keyword (str "food-group-" id "-page-" page-num)))
                                    :params        (get-page-params page-num)
                                    :error-handler error-handler}))