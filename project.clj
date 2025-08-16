(defproject subbotin-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[hiccup "1.0.5"]
                 [markdown-clj "1.10.5"]
                 [me.raynes/fs "1.4.6"]
                 [optimus "0.20.2"]
                 [org.clojure/clojure "1.12.1"]
                 [ring-logger "1.0.1"]
                 [ring/ring-core "1.8.2"]
                 [ring/ring-devel "1.8.2"]
                 [ring/ring-jetty-adapter "1.8.2"]]
  :main ^:skip-aot subbotin-web.core
  :target-path "target/%s"
  :aliases {"serve" ["run" "-m" "user/go"]
            "generate" ["run" "-m" "user/generate"]})
