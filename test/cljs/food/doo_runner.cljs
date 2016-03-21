(ns food.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [food.core-test]))

(doo-tests 'food.core-test)

