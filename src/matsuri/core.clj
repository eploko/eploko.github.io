(ns matsuri.core
  (:require
   [clojure.zip :as zip]
   [me.raynes.fs :as fs]
   [matsuri.fs :as mfs]))

(def folder-content-type "application/vnd.matsuri.folder")

(defn node
  [node-name attrs content]
  (merge {:name node-name
          :content content}
         attrs))

(defn folder
  [folder-name & children]
  (node folder-name {:content-type folder-content-type} children))

(defn page
  [page-name content-type content]
  (node page-name {:content-type content-type} content))

(defn folder?
  [node]
  (= (:content-type node) folder-content-type))

(def initial-root
  "Defines the starting point of the Static-Site Object Model (SSOM).
  This is given to the pipeline when one is ran."
  (folder "/"))

(defn valid-child-name?
  "Validates whether `x` is a valid child node name.
  A valid name should:

  - be a string.
  - not be empty.
  - should not contain a `/`."
  [x]
  (if (or (not (string? x))
          (empty? x)
          (= "/" x)
          (< 1 (count (fs/split x))))
    false
    true))

(defn ssom-zip
  "Creates a zipper to navigate the Static-Site Object Model (SSOM)."
  [root-node]
  (zip/zipper
   folder?
   :content
   (fn [node children]
     (merge node {:content-type folder-content-type
                  :content children}))
   root-node))

(defn nav-immediate-child
  "Returns loc of the immediate child node with `child-name`.
  Creates the child if one not found and `create?` is `true`."
  ([loc child-name]
   (nav-immediate-child loc child-name false))
  ([loc child-name create?]
   (when-not (valid-child-name? child-name)
     (throw (ex-info "Invalid child name."
                     {:type ::invalid-child-name
                      :child-name child-name})))
   (loop [child-loc (zip/down loc)]
     (if (nil? child-loc)
       (if create?
         (->> (folder child-name)
              (zip/append-child loc)
              zip/down
              zip/rightmost)
         nil)
       (if (= child-name (:name (zip/node child-loc)))
         child-loc
         (recur (zip/right child-loc)))))))

(defn nav
  "Returns loc of the node at `path`.
  Creates the node if one not found and `create?` is `true`."
  ([loc path]
   (nav loc path false))
  ([loc path create?]
   (let [parts (fs/split path)]
     (loop [loc loc
            target (first parts)
            remaining (rest parts)]
       (if (and loc
                (= target (:name (zip/node loc))))
         (if-let [child-name (first remaining)]
           (recur (nav-immediate-child loc child-name create?)
                  child-name
                  (rest remaining))
           loc)
         nil)))))

(defn edit-node-at-path
  [root-node path f & args]
  (zip/root
   (apply zip/edit
          (-> root-node
              ssom-zip
              (nav path true))
          f args)))

(defn edit
  [loc f & args]
  (apply zip/edit loc f loc args))

(defn walk
  [root-node f & args]
  (loop [loc (-> root-node ssom-zip)]
    (if (zip/end? loc)
      (zip/root loc)
      (recur (-> (apply f loc args) zip/next)))))

(defn run-pipeline
  [request pipeline]
  (pipeline request initial-root))

(defn loc-path
  [loc]
  (apply mfs/join-path
         (conj (vec (map :name (zip/path loc))) 
               (:name (zip/node loc)))))

(defn loc-attrs
  [loc]
  (apply merge
         (conj (vec (zip/path loc)) 
               (zip/node loc))))
