(defproject ephemeris-api "0.0.1-SNAPSHOT"
  :description "Swiss Ephemeris for Clojure"
  :min-lein-version  "2.0.0"
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java/swisseph"]
  :prep-tasks [["shell" "bin/precompile"] "javac" "compile"]
  :plugins [[lein-shell "0.5.0"]
            [lein-virgil "0.1.5"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [proto-repl "0.3.1"]
                 [clj-time "0.13.0"]]
  :repl-options {:init-ns ephemeris-api.core})
