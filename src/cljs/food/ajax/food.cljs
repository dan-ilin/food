(ns food.ajax.food
  (:require [ajax.core :refer [GET POST]]
            [reagent.session :as session]))

(defn handler [key response]
  (.log js/console (str response))
  (session/put! key response))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "Error: " status " " status-text)))

(defn get-food-groups []
  (GET "/api/food/group" {:handler       (partial handler :food-groups)
                          :error-handler error-handler}))

(defn get-food-by-group [id]
  (GET (str "/api/food/group/" id) {:handler       (partial handler id)
                                    :error-handler error-handler}))