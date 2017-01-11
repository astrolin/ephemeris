(defproject swejar "0.1.0-SNAPSHOT"
  :description "Swiss Ephemeris for Clojure"
  :min-lein-version  "2.0.0"
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java/swisseph"]
  :prep-tasks [["shell" "bin/precompile"] "javac" "compile"]
  :plugins [[lein-shell "0.5.0"]])
