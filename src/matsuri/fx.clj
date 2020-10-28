(ns matsuri.fx
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.zip :as zip]
   [hiccup.core :as hiccup]
   [markdown.core :as md]
   [matsuri.core :as m]
   [matsuri.fs :as mfs]
   [me.raynes.fs :as fs])
  (:import
   [java.io File StringReader StringWriter]))

(defn load-edn
  "Load edn from an io/reader source (filename or io/resource)."
  [source]
  (with-open [r (io/reader source)]
    (edn/read (java.io.PushbackReader. r))))

(defn folder->node
  [^File f]
  (merge {:content-type m/folder-content-type}
         (let [meta-f (io/file (mfs/join-path (.getPath f) "meta.edn"))]
           (when (.exists meta-f)
             (load-edn meta-f)))))

(defn extension->mime-type
  [s]
  (case s
    ".css" "text/css"
    ".html" "text/html"
    ".md" "text/markdown"
    ".txt" "text/plain"
    "application/octet-stream"))

(defn file->node
  [^File f]
  (if (.isDirectory f)
    (folder->node f)
    {:content-type (extension->mime-type (fs/extension (.getName f)))
     :content (slurp f)}))

(defn ignored-file?
  [filename]
  (= "meta.edn" filename))

(defn slurp-folder
  [root-node static-path]
  (loop [result root-node
         fs-loc (mfs/fs-zip static-path)]
    (if (zip/end? fs-loc)
      result
      (let [f (zip/node fs-loc)
            path (subs (.getPath f) (count static-path))]
        (recur
         (if (ignored-file? (.getName f))
           result
           (-> (m/ssom-zip result)
               (m/nav (if (empty? path) "/" path)
                      true)
               (zip/edit merge (file->node f))
               zip/root))
         (zip/next fs-loc))))))

(defn hiccup
  [root-node & args]
  (m/walk root-node zip/edit
          (fn [node]
            (if (= "text/hiccup" (:content-type node))
              (let [content (when-let [content (:content node)]
                              (if (fn? content)
                                (apply content args)
                                content))]
                (merge node
                       {:name (mfs/replace-extension (:name node) ".html")
                        :content-type "text/html"
                        :content (hiccup/html content)}))
              node))))

(defn markdown->node
  ([text]
   (markdown->node text {}))
  ([text opts]
   (let [{:keys [parse-meta? output-transformer]
          :or {parse-meta? true
               output-transformer identity}}
         opts]
     (try
       (let [input    (StringReader. text)
             output   (StringWriter.)
             metadata (md/md-to-html input output
                                     :parse-meta? parse-meta?
                                     :heading-anchors true
                                     :reference-links? true
                                     :footnotes? true)
             html     (output-transformer (.toString output))]
         (assoc (zipmap (keys metadata)
                        (map (fn [v]
                               (if (and (vector? v)
                                        (= 1 (count v)))
                                 (first v)
                                 v))
                             (vals metadata)))
                :content html))
       (catch Exception _
         (markdown->node text (merge opts {:parse-meta? false})))))))

(defn markdown
  ([root-node]
   (markdown root-node {}))
  ([root-node opts]
   (m/walk root-node zip/edit
           (fn [node]
             (if (= "text/markdown" (:content-type node))
               (merge node
                      {:name (mfs/replace-extension (:name node) ".html")
                       :content-type "text/html"}
                      (markdown->node (:content node) opts))
               node)))))


(defn layout
  [root-node layout-fn & args]
  (m/walk root-node m/edit
          (fn [node loc]
            (if (= "text/html" (:content-type node))
              (assoc node :content
                     (apply layout-fn (m/loc-attrs loc) (:content node) args))
              node))))

(comment
  (markdown {:name "index.md"
             :content-type "text/markdown"
             :content "Hello"})
  (slurp-folder m/initial-root "resources/static"))
