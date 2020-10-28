(ns matsuri.fs
  (:require
   [clojure.java.io :as io]
   [me.raynes.fs :as fs]
   [clojure.zip :as zip])
  (:import
   [java.nio.file Paths]))

(defn join-path
  [p & ps]
  (str (.normalize (Paths/get p (into-array String ps)))))

(defn replace-extension
  [name new-ext]
  (let [ext (fs/extension name)]
    (str (subs name 0 (- (count name) (count ext)))
         new-ext)))

(defn create-folders
  [path]
  (.mkdirs (.getParentFile (io/file path))))

(defn- delete-file-recursively
  [f]
  (when (.isDirectory f)
    (doseq [child (.listFiles f)]
      (delete-file-recursively child)))
  (io/delete-file f))

(defn empty-directory!
  [^String path]
  (let [f (io/file path)]
    (if (.isDirectory f)
      (doseq [child (.listFiles f)]
        (delete-file-recursively child))
      (when (.exists f)
        (throw (Exception. (str (.getPath f) " is not a directory.")))))))

(defn fs-zip
  [path]
  (zip/zipper
   #(.isDirectory %)
   #(.listFiles %)
   (fn [_old new] new)
   (io/file path)))
