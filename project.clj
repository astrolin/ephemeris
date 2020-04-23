(defproject ephemeris "0.1.1-SNAPSHOT"
  :description "Swiss Ephemeris for Clojure"
  :url "https://github.com/astrolet/ephemeris"
  :license {:name "GPL v2+ or Swiss Ephemeris"
            :url "https://github.com/astrolet/ephemeris/blob/active/LICENSE"}
  :min-lein-version  "2.0.0"
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java/ephemeris"]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clj-time "0.15.2"]]
  :jvm-opts ["--illegal-access=debug"]
  :global-vars {*warn-on-reflection* false}
  :profiles
    {:dev
      {:dependencies [[proto-repl "0.3.1"]
                      [midje "1.9.9" :exclusions [org.clojure/clojure]]
                      [midje-notifier "0.3.0"]]
       :plugins [[lein-shell "0.5.0"]
                 [lein-javac-resources "0.1.1"]
                 [lein-midje "3.2.1"]
                 [lein-ancient "0.6.15"]]
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
   :deploy-branches ["master"]
   :deploy-repositories [["clojars"
                          {:url "https://clojars.org/repo"
                           :sign-releases false
                           :creds :gpg}]])
