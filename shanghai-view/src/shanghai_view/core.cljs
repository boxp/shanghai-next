(ns shanghai-view.core
  (:require-macros [om-tools.core :refer [defcomponent]]
                   [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [put! <! chan timeout]]
            [om.core :as om]
            [om-tools.dom :include-macros true]
            [om-tools.schema]
            [sablono.core :as html :refer-macros [html]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defrecord Link [url image command])

(def links {:github (->Link "https://github.com/boxp" 
                            "/public/img/github.png"
                            "(car coll) => 'GitHubのアカウントです")
            :twitter (->Link "https://twitter.com/If_I_were_boxp" 
                             "/public/img/twitter.png"
                             "(cadr coll) => 'Twitterのアカウントです")
            :niconico (->Link "http://www.nicovideo.jp/user/6265398" 
                              "/public/img/niconico.png"
                              "(caddr coll) => 'ニコニコ動画のアカウントです")})

(def icon-size 120)
(def interval 70)

(defn- on-mouse-over
  [k owner]
  (om/set-state! owner :focus k))

(defn link->box
  [link owner]
  [:div {:class "box"
         :on-mouse-over #(on-mouse-over (key link) owner)}
    [:a {:href (-> link val :url)}
      [:img {:src (-> link val :image)}]]])

(defn- write-char
  [s owner]
  (om/update-state! owner :buffer #(str % s)))

(defn typer
  [c owner]
  (go (loop [last-focus nil
             focus (om/get-state owner :focus)]
        (if (= last-focus focus)
          (recur focus (<! c))
          (do
            (om/set-state! owner :buffer "")
            (doseq [s (-> links focus :command)]
              (write-char s owner)
              (<! (timeout interval)))
            (recur focus (<! c)))))))

(defcomponent view [_ owner]
  (init-state [_]
    {:focus :github
     :buffer ""
     :writer (chan 1)})
  (did-mount [_]
    (typer (om/get-state owner :writer) owner))
  (will-update [_ _ data]
    (put! (om/get-state owner :writer) (om/get-state owner :focus)))
  (render-state [_ data]
    (html [:div
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
              [:div {:class "bracket"} ")"]]])))

(om/root view {}
  {:target (. js/document (getElementById "app"))})
