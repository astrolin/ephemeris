(ns ephemeris.core
  (:require
    [ephemeris.time :refer (utc-to-jd)]
    [ephemeris.points :refer (lookup known?)])
  (:import (swisseph SwissEph SweConst)))

(def ^:private
  defaults {:utc nil
            :geo {:lat nil :lon nil}
            :bodies []
            :angles true
            :houses "O"
            :meta false})

(defn- request
  ([] defaults)
  ([stuff]
   (let [want (merge defaults stuff)
         jd (utc-to-jd (:utc want))]
    (assoc want :jd jd))))

(defn- geo? [geo]
  (if (and
        (map? geo)
        (contains? geo :lat)
        (contains? geo :lon)
        (number? (:lat geo))
        (number? (:lon geo)))
    true
    false))

(defn- angles? [what]
  (or (= true what) (and (vector? what) (> (count what) 0))))

(defn- houses? [what]
  (boolean what))

(defn- coerce-houses [hs]
  (if (= (type hs) java.lang.Character)
    hs
    (if (and
          (= (type hs) java.lang.String)
          (> (count hs) 0))
      (get hs 0)
      \O)))

(defn calc [stuff]
  (let [sw (SwissEph.)
        want (request stuff)
        flag (. SweConst SEFLG_SPEED)]
    (merge
      {:bodies
        (into {}
          (for [i (flatten [(:bodies want)])]
            (let [what (if (known? i) (lookup i) i)
                  res (double-array 6)
                  err (StringBuffer.)
                  rc (.swe_calc_ut sw
                                   (:jd want)
                                   what
                                   flag
                                   res
                                   err)]
              {(lookup what) {:lon (aget res 0)
                              :lat (aget res 1)
                              :sdd (aget res 3)}})))}
      (if (and (geo? (:geo want))
               (or (angles? (:angles want))
                   (houses? (:houses want))))
        (let [cusps (double-array 13)
              ascmc (double-array 10)
              rc (.swe_houses sw
                              (:jd want)
                              0 ;; TODO: flags?
                              (:lat (:geo want))
                              (:lon (:geo want))
                              (int (coerce-houses (:houses want)))
                              cusps
                              ascmc)]
          (merge
            (if (angles? (:angles want))
              {:angles (subvec (vec ascmc) 0 8)}
              {})
            (if (houses? (:houses want))
              {:houses (zipmap (range 1 13) (rest cusps))}
              {}))))
      (if (boolean (:meta want))
        {:meta (dissoc want :meta)}
        {}))))
