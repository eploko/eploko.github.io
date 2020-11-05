(ns subbotin-web.data
  (:require
   [clojure.java.shell :refer [sh]]
   [clojure.string :as str]))

(def site-title
  "Andrey Subbotin")

(def email
  "andrey@subbotin.me")

(def elsewhere
  "My social accounts."
  [{:href "https://github.com/eploko"
    :icon [:fab :fa-github]}
   {:href "https://stackoverflow.com/users/355357/eploko"
    :icon [:fab :fa-stack-overflow]}
   {:href "https://dribbble.com/eploko"
    :icon [:fab :fa-dribbble]}
   {:href "https://www.linkedin.com/in/asubbotin/"
    :icon [:fab :fa-linkedin]}])

(def pgp-fingerprint
  (str/trim-newline (:out (sh "gpg" "--fingerprint" email))))

(def pgp-public-key
  (:out (sh "gpg" "--armor" "--export" email)))

(def pgp-fingerprint-parts
  (->> (re-seq #"[0-9A-F]{4}\s" pgp-fingerprint)
     (drop 1)
     (take 10)
     (map str/trim)
     (partition 5)
     (map (partial str/join " "))
     vec))
