(ns shanghai.routes
  (:require [shanghai.component.app :as comp-app]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [om.core :as om]
            [secretary.core :as secretary :refer-macros [defroute]])
  (:import goog.History))

(enable-console-print!)

(secretary/set-config! :prefix "#")

(def application (. js/document (getElementById "app")))

(defn set-component
  [el component]
  (om/root component {}
    {:target el}))

(defroute index "/" []
  (set-component application comp-app/app))

;; Quick and dirty history configuration.
(let [h (History.)]
  (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h (.setEnabled true)))
