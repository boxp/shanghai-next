(ns shanghai.view.app
  (:require [shanghai.view.app.link :refer [link->box]]
            [sablono.core :as html :refer-macros [html]]))

(def icon-size 120)

(defn app 
  [owner links data]
  (html
    [:div
      [:div#cmd
        [:code (->> data :buffer)]
        [:span "▮"]]
      [:div#links {:style 
                    {:width (str 
                              (* icon-size (+ (* (count links) 2) 1)) 
                              "px")}}
        [:div {:class "bracket"} "'("]
        (->> links (map #(link->box % owner)) 
             (interpose [:div {:class "bracket"} " "]))
        [:div {:class "bracket"} ")"]]]))
