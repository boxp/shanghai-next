(ns shanghai.view.app
  (:require [shanghai.view.app.link :refer [link->box]]
            [sablono.core :as html :refer-macros [html]]))

(def icon-size 120)

(defn app
  [owner links data]
  (html
    [:div "This is test!!!!!!"]))
