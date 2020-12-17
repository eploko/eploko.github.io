(ns subbotin-web.timeline
  (:require
   [markdown.core :as md]))

(defn gen-timeline-item
  [x]
  [:div
   [:dt {:class "text-xs font-bold text-red-600"}
    (:when x)]
   [:dd
    (md/md-to-html-string (:what x))]])

(defn timeline->hiccup
  [timeline]
  [:dl
   (map gen-timeline-item (reverse timeline))])
