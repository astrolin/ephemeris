(ns ephemeris.core
  (:require [ephemeris.time :refer (utc-to-jd)]
            [ephemeris.points :refer (lookup known?)])
  (:import (swisseph SwissEph SweConst)))

(def ^:private
  defaults {:utc nil
            :geo {:lat nil :lon nil}
            :points [:Sun :Moon :Mercury :Venus :Mars :Jupiter :Saturn
                     :TrueNode :Uranus :Neptune :Pluto]
            :angles [:Asc :MC]
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

(defn- nice-angles [data wanted]
  (let [sub (subvec (vec data) 0 8)
        all (zipmap
              [:Asc
               :MC
               :ARMC
               :Vertex
               :EquatAsc ;; equatorial ascendant
               :Co-Asc1 ;; co-ascendant (W. Koch)
               :Co-Asc2 ;; co-ascendant (M. Munkasey)
               :PolarAsc] ;; (M. Munkasey)
              sub)]
    (if (angles? wanted)
      (if (or (= wanted true) (not (vector? wanted)))
        {:angles all}
        {:angles (select-keys all wanted)})
      {})))

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

(defn- nice-houses [data wanted]
  (if (houses? wanted)
    {:houses (zipmap (range 1 13) (rest data))}
    {}))

(defn calc [stuff]
  (let [sw (SwissEph.)
        want (request stuff)
        flag (. SweConst SEFLG_SPEED)]
    (merge
      {:points
        (into {}
          (for [i (flatten [(:points want)])]
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
            (nice-angles ascmc (:angles want))
            (nice-houses cusps (:houses want)))))
      (if (:meta want) {:wanted (dissoc want :meta)} {}))))
