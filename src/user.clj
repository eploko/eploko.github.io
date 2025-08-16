(ns user
  (:require
   [matsuri.generator :as generator]
   [matsuri.server :as server]
   [subbotin-web.core :as web]))

(defonce !server (atom nil))

(defn go
  ([]
   (go false))
  ([join?]
   (reset! !server
           (server/serve #'web/pipeline
                         {:assets-fn web/get-assets
                          :ring
                          {:join? join?
                           :port 3000}}))))

(defn stop
  []
  (when-let [server @!server]
    (.stop server)
    (reset! !server nil)))

(defn restart
  []
  (stop)
  (go))

(defn generate
  []
  (generator/generate web/pipeline
                      {:assets-fn web/get-assets}))

(comment
  (restart)

  (generate)
  ,)
