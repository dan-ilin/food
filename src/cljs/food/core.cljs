(ns food.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [food.init :refer [load-interceptors!]]
            [ajax.core :refer [GET POST]]
            [food.pages.main :as main-pages]
            [food.pages.food :as food-pages])
  (:import goog.History))

(defn nav-link [uri title page collapsed?]
  [:li.nav-item
   {:class (when (= page (session/get :page)) "active")}
   [:a.nav-link
    {:href     uri
     :on-click #(reset! collapsed? true)} title]])

(defn navbar []
  (let [collapsed? (r/atom true)]
    (fn []
      [:nav.navbar.navbar-light.bg-faded
       [:button.navbar-toggler.hidden-sm-up
        {:on-click #(swap! collapsed? not)} "☰"]
       [:div.collapse.navbar-toggleable-xs
        (when-not @collapsed? {:class "in"})
        [:a.navbar-brand {:href "#/"} "food"]
        [:ul.nav.navbar-nav
         [nav-link "#/" "Home" :home collapsed?]
         [nav-link "#/about" "About" :about collapsed?]
         [nav-link "#/food/groups" "Food Groups" :food-groups collapsed?]]]])))

(def pages
  {:home        #'main-pages/home-page
   :about       #'main-pages/about-page
   :food-groups #'food-pages/food-group-list
   :food        #'food-pages/food-page
   :nutrition   #'food-pages/nutrition-page})

(defn page []
  [(pages (session/get :page))])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
                    (session/put! :page :home))

(secretary/defroute "/about" []
                    (session/put! :page :about))

(secretary/defroute "/food/groups" []
                    (session/put! :page :food-groups))

(secretary/defroute "/food/group/:id" [id query-params]
                    (let [page (:page query-params)]
                      (session/put! :food-group-id id)
                      (session/put! :page-num (if (nil? page) 1 (int page)))
                      (session/put! :page :food)))

(secretary/defroute "/food/:id/nutrition" [id]
                    (session/put! :food-id id)
                    (session/put! :page :nutrition))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn fetch-docs! []
  (GET (str js/context "/docs") {:handler #(session/put! :docs %)}))

(defn mount-components []
  (r/render [#'navbar] (.getElementById js/document "navbar"))
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (load-interceptors!)
  (fetch-docs!)
  (hook-browser-navigation!)
  (mount-components))
