(ns ephemeris.core
  (:import (swisseph SwissEph SweDate SweConst)))

(defn calc-now [what]
  (let [sw (SwissEph.)
        sd (SweDate.)
        jd (.getJulDay sd)
        res (double-array 6)
        err (StringBuffer.)]
    (do
      (.swe_calc_ut sw jd
                    what
                    (. SweConst SEFLG_SPEED)
                    res
                    err)
      {what
        {:lon (aget res 0)
         :lat (aget res 1)
         :lsd (aget res 3)}})))

(comment
  (calc-now (. SweConst SE_MARS)))
