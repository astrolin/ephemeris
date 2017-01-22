(defproject ephemeris "0.0.1"
  :description "Swiss Ephemeris for Clojure"
  :min-lein-version  "2.0.0"
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java/ephemeris"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-time "0.13.0"]]
  :profiles
    {:dev
      {:dependencies [[proto-repl "0.3.1"]
                      [midje "1.8.3" :exclusions [org.clojure/clojure]]
                      [midje-notifier "0.2.0"]]
       :plugins [[lein-shell "0.5.0"]
                 [lein-javac-resources "0.1.1"]
                 [lein-midje "3.2.1"]]
       :prep-tasks [["shell" "bin/precompile"] "javac" "compile"]
       :hooks [leiningen.javac-resources]
       :repl-options {:init-ns ephemeris.core}}
     :repl
      {:ultra {:repl {:sort-keys false
                      :map-coll-separator :line}}}
     :uberjar {:aot :all
               :omit-source true}}
   :aliases {"test" ["midje"]
             "autotest" ["midje" ":autotest"]}
   :main ephemeris.main
   :jar-name "ephemeris.jar"
   :uberjar-name "ephemerist.jar"
   :pom-location "target/"
   :deploy-branches ["master"])
