(ns matsuri.core-test
  (:require [matsuri.core :as sut]
            [clojure.test :as t :refer [deftest is]]
            [clojure.zip :as zip]))

(deftest test-ssom-zip
  (let [data (sut/folder "/"
                         (sut/page "index.html" "text/html" "")
                         (sut/folder "matsuri"
                                   (sut/page "index.html" "text/html" "")))
        root-loc (sut/ssom-zip data)]
    (is (= data (zip/node root-loc)))
    (is (= (sut/page "index.html" "text/html" "")
           (-> root-loc zip/down zip/node)))
    (is (= (sut/folder "matsuri"
                       (sut/page "index.html" "text/html" ""))
           (-> root-loc zip/down zip/right zip/node)))    
    (is (= (sut/page "index.html" "text/html" "")
           (-> root-loc zip/down zip/right zip/down zip/node)))))

(deftest test-nav-immediate-child
  (let [data (sut/folder "/"
                         (sut/page "index.html" "text/html" "")
                         (sut/folder "matsuri"))
        root-loc (sut/ssom-zip data)]
    (is (= (-> root-loc zip/down)
           (sut/nav-immediate-child root-loc "index.html")))
    (is (= (-> root-loc zip/down zip/right)
           (sut/nav-immediate-child root-loc "matsuri")))
    (is (thrown? Exception
                 (sut/nav-immediate-child root-loc "/")))
    (is (thrown? Exception
                 (sut/nav-immediate-child root-loc "/starts-with-a-slash")))
    (is (nil? (sut/nav-immediate-child root-loc "non-existent")))
    (let [child-loc (sut/nav-immediate-child root-loc "non-existent" true)]
      (is (= (->> child-loc zip/root sut/ssom-zip zip/down zip/rightmost zip/node)
             (zip/node child-loc)))
      (is (= (sut/folder "non-existent") (zip/node child-loc))))))

(deftest test-valid-child-name?
  (is (true? (sut/valid-child-name? "index.html")))
  (is (true? (sut/valid-child-name? "somename")))
  (is (false? (sut/valid-child-name? "/index.html")))
  (is (false? (sut/valid-child-name? "/somename")))
  (is (false? (sut/valid-child-name? "/")))
  (is (false? (sut/valid-child-name? "")))
  (is (false? (sut/valid-child-name? nil)))
  (is (false? (sut/valid-child-name? 'not-a-string))))

(deftest test-nav
  (let [data (sut/folder "/"
                         (sut/page "index.html" "text/html" "")
                         (sut/folder "matsuri"
                                   (sut/page "index.html" "text/html" "")))
        root-loc (sut/ssom-zip data)]
    (is (= root-loc (sut/nav root-loc "/")))
    (is (= (-> root-loc zip/down)
           (sut/nav root-loc "/index.html")))
    (is (= (-> root-loc zip/down zip/right)
           (sut/nav root-loc "/matsuri/")))    
    (is (= (-> root-loc zip/down zip/right zip/down)
           (sut/nav root-loc "/matsuri/index.html")))
    (is (nil? (sut/nav root-loc "/non-existing")))
    (let [target-loc (sut/nav root-loc "/non/existing" true)]
      (is target-loc)
      (is (= (sut/folder "existing") (zip/node target-loc))))))

(deftest test-folder
  (is (= {:name "/"
          :content-type sut/folder-content-type
          :content nil}
         (sut/folder "/")))
  (is (= {:name "/"
          :content-type sut/folder-content-type
          :content
          [{:name "index.html"
            :content-type "text/html"
            :content ""}]}
         (sut/folder "/"
                     (sut/page "index.html" "text/html" "")))))

(deftest edit-node-at-path-test
  (let [data (sut/folder "/")]
    (is (= (assoc data :x true)
           (sut/edit-node-at-path data "/" assoc :x true)))
    (is (= (assoc data :content
                  [(assoc (sut/folder "matsuri") :x true)])
           (sut/edit-node-at-path data "/matsuri" assoc :x true)))
    (is (= (assoc data :content
                  [(assoc (sut/folder "matsuri") :content
                          [(assoc (sut/folder "index") :x true)])])
           (sut/edit-node-at-path data "/matsuri/index" assoc :x true)))))

