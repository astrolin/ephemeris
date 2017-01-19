(ns ephemeris.main
  (:require [ephemeris.core :refer (calc)]
            [clojure.edn :as edn]
            [clojure.pprint :refer (pprint)])
  (:gen-class))

(defn -main [& args]
  (pprint
    (if args
      (calc (edn/read-string (first args)))
      (calc))))
