(ns subbotin-web.core
  (:require
   [clojure.string :as str]
   [hiccup.core :as hiccup]
   [hiccup.page :refer [html5]]
   [matsuri.core :as m]
   [matsuri.fx :as fx]
   [optimus.assets :as assets]
   [optimus.link :as link]
   [plokodelika.toolbelt :as tb]
   [subbotin-web.data :as data])
  (:import
   [java.time ZonedDateTime Year]
   [java.time.format DateTimeFormatter])
  (:gen-class))

(defn page-title
  [title]
  (if title
    (str title " &mdash; " data/site-title)
    data/site-title))

(def current-year
  (.getValue (Year/now)))

(def generated-at
  (let [moment (ZonedDateTime/now)]
    (str
     (.format moment
              (DateTimeFormatter/ofPattern "EEEE, MMMM d, YYYY"))
     " at "
     (.format moment
              (DateTimeFormatter/ofPattern "h:mm a z")))))

(defn get-assets
  []
  (assets/load-assets "assets" [#"/(images|css)/.*"]))

(defn wrap-md-with-prose
  [text]
  (hiccup/html
   [:div {:class "prose lg:prose-xl"} text]))

(defn google-analytics
  "Global site tag (gtag.js) - Google Analytics"
  [measurement-id]
  (list [:script {:async true
                  :src (str "https://www.googletagmanager.com/gtag/js?id=" measurement-id)}]
        [:script (str "window.dataLayer = window.dataLayer || [];"
                      "function gtag(){dataLayer.push(arguments);}"
                      "gtag('js', new Date());"
                      "gtag('config', '" measurement-id "');")]))

(defn fa
  "A Font Awesome icon with the given name."
  [x-or-opts & xs]
  (let [opts (if (map? x-or-opts) x-or-opts {})]
    [:span (merge opts
                  {:class (str/join " " (map name (if (map? x-or-opts)
                                                    xs
                                                    (cons x-or-opts xs))))})]))

(defn href
  [opts & children]
  (let [rest-opts (dissoc opts :class :plain)]
    (into
     [:a
      (merge rest-opts
             {:class (tb/cx (:class opts)
                            "text-linkiro hover:text-green transition duration-200"
                            {"underline" (not (:plain opts))})})]
     children)))

(defn header
  []
  [:header {:class "p-6 md:p-8 lg:p-12 xl:p-16 bg-manila-light border-b border-gray-200"}
   [:p "&nbsp;"
    #_[:span "About | Projects | Essays | CV"]]])

(defn footer
  []
  [:footer {:class "p-6 md:p-8 lg:p-12 xl:p-16 bg-manila-light border-t border-gray-100"}
   [:p "Built with Emacs, Clojure, Matsuri, Tailwind CSS, Sketch, and heaps of experience."]
   [:p.mt-4.text-xl
    (href {:plain true
           :rel "license"
           :href "https://creativecommons.org/licenses/by-sa/4.0/"}
          (fa :fab :fa-creative-commons)
          " "
          (fa :fab :fa-creative-commons-by)
          " "
          (fa :fab :fa-creative-commons-sa))]
   [:p.mt-4
    (str "Copyright &copy; 1993&ndash;"
         current-year
         " Andrey Subbotin.")]
   [:p {:class "mt-4 md:mt-0"}
    "This work is licensed under "
    (href
     {:rel "license"
      :href "https://creativecommons.org/licenses/by-sa/4.0/"}
     "Creative Commons Attribution-ShareAlike 4.0 International License")
    "."]
   [:p.mt-4 (str "Last updated on " generated-at ".")]])

(defn elsewhere
  [xs]
  [:ul {:class "my-8 flex flex-row"}
   (map (fn [x]
          [:li {:class "text-2xl mr-6 md:text-3xl md:mr-8 2xl:text-5xl xl:mt-8 2xl:mt-16"}
           (href
            {:plain true
             :href (:href x)}
            (apply fa (:icon x)))])
        xs)])

(defn contact-form
  []
  [:div {:class "bg-manila"}
   [:div {:class "p-6 md:p-8 lg:p-12 xl:p-16 lg:max-w-screen-md"}
    [:h3 {:class "mb-4 text-2xl font-medium"} "Say hello"]
    [:p "If you have any questions, ideas, or thoughts to share, please feel free to reach me via "
     (href {:href "mailto:andrey@subbotin.me"} "andrey@subbotin.me")]]])

(defn layout
  [attrs body request]
  (html5 {:lang "en"}
         [:head
          (google-analytics "G-MH7W1FTWXV")
          [:meta {:charset "utf-8"}]
          [:meta {:name "viewport"
	          :content "width=device-width, initial-scale=1.0"}]
          [:title (page-title (:title attrs))]
          [:link {:rel "stylesheet" :href (link/file-path request "/css/main.css")}]
          [:script {:src "https://kit.fontawesome.com/3254fbb503.js"
                    :crossorigin "anonymous"}]]
         [:body.min-h-screen.flex.flex-col.bg-manila-light
          (header)
          [:div {:class "flex-1 bg-manila-light"}
           body]
          (footer)
          #_[:code (hiccup/h (str request))]]))

(defn home-page
  [request]
  [:div
   [:div {:class "flex flex-col md:flex-row border-b border-gray-100"}
    [:div {:class "p-6 md:p-8 lg:p-12 xl:p-16 pb-0 md:pb-0 lg:pb-0 xl:pb-0 flex flex-col border-b border-gray-100 md:border-none lg:max-w-2/3 xl:max-w-1/2"}
     [:p {:class "text-xl sm:text-2xl font-medium"} "Hey! "]
     [:h1 {:class "text-3xl sm:text-4xl lg:text-5xl xl:text-6xl 2xl:text-8xl pb-4 font-extrabold"} "I'm Andrey Subbotin."]
     [:p {:class "md:text-xl lg:text-2xl lg:font-light 2xl:text-4xl"}
      "I've been digging all things "
      [:span.bg-highlighting "software development professionally since 1998."]
      " I don't write code. I don't code software. "
      "I meticulously culture it."]
     (elsewhere data/elsewhere)]
    [:img {:class "sm:w-1/2 md:w-1/3 xl:w-1/4 2xl:w-1/5 md:-mt-20 lg:-mt-24 xl:-mt-32 sm:self-center md:self-auto"
           :src (link/file-path request "/images/andrey-subbotin.gif")}]]
   (contact-form)])

(defn matsuri-page
  [_request]
  [:div
   [:h1 "Matsuri"]
   [:p "Matsuri is a static site generator written in Clojure."]])

(defn not-found-page
  [_request]
  [:div
   [:h1 "Page Not Found"]
   [:h2 "Error 404"]
   [:p "The requested page is not found on this site."]])

(defn pipeline
  [request root-node]
  (-> root-node
      (fx/slurp-folder "resources/static")
      (m/edit-node-at-path "/index"
                           merge
                           {:content-type "text/hiccup"
                            :content home-page})
      (m/edit-node-at-path "/matsuri/index"
                           merge
                           {:title "Matsuri: Static Site Generator"
                            :content-type "text/hiccup"
                            :content matsuri-page})
      (m/edit-node-at-path "/404"
                           merge
                           {:title "Error 404: Page Not Found"
                            :content-type "text/hiccup"
                            :content not-found-page})
      (fx/hiccup request)
      (fx/markdown {:output-transformer wrap-md-with-prose})
      (fx/layout layout request)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World :)"))

(comment
  (m/run-pipeline pipeline))
