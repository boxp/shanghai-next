(ns shanghai.view.app.link
  (:require [shanghai.component.app.link :as comp-links]))

(defn link->box
  [link owner]
  [:div {:class "box"
         :on-mouse-over #(comp-links/on-mouse-over (key link) owner)}
    [:a {:href (-> link val :url)}
      [:img {:src (-> link val :image)}]]])
