(ns shanghai.component.app
  (:require-macros [om-tools.core :refer [defcomponent]]
                   [cljs.core.async.macros :refer [go]])
  (:require [shanghai.view.app :as app-view]
            [cljs.core.async :refer [put! <! chan timeout]]
            [om.core :as om]
            [om-tools.dom :include-macros true]
            [om-tools.schema]
            [sablono.core :as html :refer-macros [html]]))

(def interval 70)

(defrecord Link [url image command])

(def links {:github (->Link "https://github.com/boxp"
                            "/img/github.png"
                            "(car coll) => 'GitHubのアカウントです")
            :twitter (->Link "https://twitter.com/If_I_were_boxp"
                             "/img/twitter.png"
                             "(cadr coll) => 'Twitterのアカウントです")
            :niconico (->Link "http://www.nicovideo.jp/user/6265398"
                              "/img/niconico.png"
                              "(caddr coll) => 'ニコニコ動画のアカウントです")})

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

(defcomponent app [_ owner]
  (init-state [_]
    {:focus :github
     :buffer ""
     :writer (chan 1)})
  (did-mount [_]
    (typer (om/get-state owner :writer) owner))
  (will-update [_ _ data]
    (put! (om/get-state owner :writer) (om/get-state owner :focus)))
  (render-state [_ data]
    (app-view/app owner links data)))
