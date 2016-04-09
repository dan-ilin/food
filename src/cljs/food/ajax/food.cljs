(ns food.ajax.food
  (:require [ajax.core :refer [GET POST]]))

(defn handler
  [atom response]
   (.log js/console (str response))
   (reset! atom response))
;([atom key response]
;  (.log js/console (str response))
;  (swap! atom assoc key response))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console
        (str "Error: " status " " status-text)))

(defn get-food-groups [atom]
  (GET "/api/food/group" {:handler       (partial handler atom)
                          :error-handler error-handler}))

(defn get-food-by-group [atom id]
  (GET (str "/api/food/group/" id) {:handler       (partial handler atom id)
                                    :error-handler error-handler}))