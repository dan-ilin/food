(ns user
  (:require [mount.core :as mount]
            [food.figwheel :refer [start-fw stop-fw cljs]]
            food.core))

(defn start []
  (mount/start-without #'food.core/repl-server))

(defn stop []
  (mount/stop-except #'food.core/repl-server))

(defn restart []
  (stop)
  (start))


