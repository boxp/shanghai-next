(ns shanghai.routes
  (:require [shanghai.view.index :as index-view]
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

;; Quick and dirty history configuration.
(let [h (History.)]
  (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h (.setEnabled true)))
