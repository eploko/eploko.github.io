(ns matsuri.generator
  (:require
   [clojure.zip :as zip]
   [matsuri.assets :as assets]
   [matsuri.core :as m]
   [matsuri.fs :as mfs]
   [optimus.optimizations :as optimizations]
   [optimus.export]))

(defn- emit-file
  [path content]
  (spit path content))

(defn emit-node
  [path node]
  (println "emitting:" path)
  (mfs/create-folders path)
  (emit-file path (:content node)))

(defn make-assets
  [assets-fn]
  (println "generating assets...")
  (as-> (assets-fn) assets
        (optimizations/all assets {})
        (remove :bundled assets)
        (remove :outdated assets)))

(defn generate
  ([pipeline]
   (generate pipeline {}))
  ([pipeline opts]
   (let [{:keys [assets-fn]
          :or {assets-fn assets/no-assets}}
         opts

         path "docs"

         site-assets (make-assets assets-fn)
         request {:optimus-assets site-assets}]
     (mfs/empty-directory! path)
     (loop [loc (->> pipeline
                     (m/run-pipeline request)
                     m/ssom-zip)]
       (when-not (zip/end? loc)
         (when-not (m/folder? (zip/node loc))
           (emit-node (mfs/join-path path (m/loc-path loc))
                      (zip/node loc)))
         (recur (zip/next loc))))
     (doseq [asset site-assets]
       (println "asset:" (mfs/join-path path (:path asset))))
     (optimus.export/save-assets site-assets path))))
