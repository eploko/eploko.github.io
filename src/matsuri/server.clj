(ns matsuri.server
  (:require
   [clojure.zip :as zip]
   [matsuri.assets :as assets]
   [matsuri.core :as m]
   [matsuri.fs :as fs]
   [optimus.optimizations :as optimizations]      
   [optimus.prime :as optimus]                    
   [optimus.strategies :refer [serve-live-assets-autorefresh]]   
   [ring.adapter.jetty :refer [run-jetty]]
   [ring.logger :as logger]
   [ring.util.response :as response]))

(defn- get-node
  [loc path]
  (when-let [target-loc (m/nav loc path)]
    (zip/node target-loc)))

(defn- not-found
  [loc]
  (response/not-found
   (or (:content (get-node loc "/404.html"))
       "Page not found.")))

(defn handler
  [pipeline]
  (fn [request]
    (let [root-node (m/run-pipeline request pipeline)
          root-loc (m/ssom-zip root-node)]
      (loop [uri (:uri request)]
        (if-let [node (get-node root-loc uri)]
          (if (m/folder? node)
            (recur (fs/join-path uri "index.html"))
            (-> (response/response (:content node))
                (response/content-type (:content-type node))))
          (not-found root-loc))))))

(defn serve
  ([pipeline]
   (serve pipeline {}))
  ([pipeline opts]
   (let [{:keys [assets-fn ring]
          :or {assets-fn assets/no-assets
               ring {}}}
         opts]
     (run-jetty
      (-> (handler pipeline)
          (optimus/wrap
           assets-fn
           optimizations/all
           serve-live-assets-autorefresh
           {:assets-dir "resources/assets"})
          logger/wrap-with-logger)
      ring))))


