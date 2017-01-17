(defproject ephemeris-api "0.0.1-SNAPSHOT"
  :description "Swiss Ephemeris for Clojure"
  :min-lein-version  "2.0.0"
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java/swisseph"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-time "0.13.0"]]
  :profiles
    {:dev
      {:dependencies [[proto-repl "0.3.1"]]
       :plugins [[lein-shell "0.5.0"]]
       :prep-tasks [["shell" "bin/precompile"] "javac" "compile"]}
     :repl
      {:repl-options {:init-ns ephemeris-api.core}
       :ultra {:repl {:sort-keys false
                      :map-coll-separator :line}}}})
