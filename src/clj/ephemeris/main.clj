(ns ephemeris.main
  (:require [clojure.pprint :refer (pprint)]
            [ephemeris.core :refer (calc)])
  (:gen-class))

(defn -main [& args]
  (pprint (calc)))
