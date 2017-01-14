(ns ephemeris.core
  (:require
    [ephemeris.time :refer (utc-to-jd)]
    [ephemeris.points :refer (lookup known?)])
  (:import (swisseph SwissEph SweConst)))

(def defaults {:utc nil
               :geo {:lat nil :lon nil}
               :bodies []
               :angles []
               :houses \O})

(defn calc [stuff]
  (let [want (merge defaults stuff)
        sw (SwissEph.)
        jd (utc-to-jd (:utc want))
        flag (. SweConst SEFLG_SPEED)]
    (merge
      (into {}
        (for [i (flatten [(:bodies want)])]
          (let [what (if (known? i) (lookup i) i)
                res (double-array 6)
                err (StringBuffer.)
                rc (.swe_calc_ut sw
                                 jd
                                 what
                                 flag
                                 res
                                 err)]
            {(lookup what) {:lon (aget res 0)
                            :lat (aget res 1)
                            :sdd (aget res 3)}})))
      (if (not (empty? (:angles want)))
        (let [cusps (double-array 13)
              ascmc (double-array 10)
              rc (.swe_houses sw
                              jd
                              0 ;; what flag?
                              (:lat (:geo want))
                              (:lon (:geo want))
                              (int (:houses want))
                              cusps
                              ascmc)]
          (zipmap (range 1 13) (rest cusps)))))))
