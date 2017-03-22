(ns shanghai.component.app.link
  (:require [om.core :as om]))

(defn on-mouse-over
  [k owner]
  (om/set-state! owner :focus k))
