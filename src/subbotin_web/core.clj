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
     [:a.text-linkiro
      (merge rest-opts
             {:class (tb/cx (:class opts)
                            {"underline" (not (:plain opts))})})]
     children)))

(defn header
  []
  [:header.py-8.px-16.bg-manila-light.border-b.border-gray-200
   [:p "&nbsp;"
    #_[:span "About | Projects | Essays | CV"]]])

(defn footer
  []
  [:footer.py-8.px-16.bg-manila-light
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
   [:p
    "This work is licensed under "
    (href
     {:rel "license"
      :href "https://creativecommons.org/licenses/by-sa/4.0/"}
     "Creative Commons Attribution-ShareAlike 4.0 International License")
    "."]
   [:p.mt-4 (str "Last updated on " generated-at ".")]])

(defn elsewhere
  [xs]
  [:ul.mt-16.text-5xl.flex.flex-row
   (map (fn [x]
          [:li.mr-6
           (href
            {:plain true
             :href (:href x)}
            (apply fa (:icon x)))])
        xs)])

(defn contact-form
  []
  [:div.max-w-xl
   [:h3.text-2xl.font-medium "Say hello"]
   [:form.mt-6.flex.flex-col
    {:action "https://formspree.io/f/mwkwbqkg"
     :method "POST"}
    [:label.flex.flex-col
     "Your email:"
     [:input
      {:class "border p-2 leading-loose font-mono outline-none focus:border-linkiro transition duration-200"
       :type "email"
       :spell-check false
       :placeholder "yourname@example.com"
       :name "_replyto"
       :required true}]]
    [:label.mt-4.flex.flex-col
     "Message:"
     [:textarea
      {:class "border p-2 leading-loose font-mono outline-none focus:border-linkiro transition duration-200"
       :name "message"
       :rows 6
       :placeholder "Hey Andrey,\n…"
       :required true}]]
    [:button.self-start.mt-6.px-4.py-2.bg-green.text-white
     {:class (tb/cx "p-4 text-lg outline-none focus:outline-none"
                    "text-opacity-95 rounded border border-transparent"
                    "transition duration-200"
                    "bg-green hover:bg-green-600 focus:bg-green-600"
                    "active:bg-green-700 focus:border-linkiro"
                    "text-white cursor-pointer shadow-sm")
      :type "submit"}
     [:span.mr-2 "Send"]
     (fa :fas :fa-paper-plane)]]])

(defn layout
  [attrs body request]
  (html5 {:lang "en"}
         [:head
          [:meta {:charset "utf-8"}]
          [:meta {:name "viewport"
	          :content "width=device-width, initial-scale=1.0"}]
          [:title (page-title (:title attrs))]
          [:link {:rel "stylesheet" :href (link/file-path request "/css/main.css")}]
          [:script {:src "https://kit.fontawesome.com/3254fbb503.js"
                    :crossorigin "anonymous"}]]
         [:body.min-h-screen.flex.flex-col.bg-manila-light
          (header)
          [:div.flex-1.py-8.px-16.bg-manila-light body]
          (footer)
          #_[:code (hiccup/h (str request))]]))

(defn home-page
  [request]
  [:div
   [:div.pt-8.flex.flex-row.border-b.border-gray-200
    [:div
     [:p.text-2xl.font-medium "Hey! "]
     [:h1.text-6xl.font-extrabold "I'm Andrey Subbotin."]
     [:p.text-xl.max-w-xl
      "I've been digging all things "
      [:span.bg-highlighting "software development professionally since 1998."]
      " I don't write code. I don't code software. "
      "I meticulously culture it."]
     (elsewhere data/elsewhere)]
    [:img.HeyPic {:src (link/file-path request "/images/andrey-subbotin.gif")}]]
   [:div.py-16.border-b.border-gray-200
    (contact-form)]])

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
