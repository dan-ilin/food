(ns food.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [food.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[food started successfully using the development profile]=-"))
   :middleware wrap-dev})
