(ns ephemeris.core
  (:require [ephemeris.time :refer (utc-to-jd)]
            [ephemeris.points :refer (lookup known?)]
            [clojure.set :refer (difference)])
  (:import (swisseph SwissEph SweConst)))

(def ^:private
  defaults {:utc nil
            :geo {:lat nil :lon nil}
            :points [:Sun :Moon :Mercury :Venus :Mars :Jupiter :Saturn
                     :TrueNode :Uranus :Neptune :Pluto]
            :angles [:Asc :MC]
            :houses "O"
            :meta true})

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

(defn- nice-angles [data wanted re]
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
        (do
          (let [unknown (difference (set wanted) (set (keys all)))]
            (if (< 0 (count unknown))
              (result re [:unknown :angles] (vec unknown))))
          {:angles (select-keys all wanted)}))
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

;; expects an atom map, key, and value to assoc
;; alternatively, k can be a vector of keys where get-in yields a vector value
;; returns the value for further use
(defn- result [a k v]
  (do
    (if (vector? k)
      (reset! a (assoc-in @a k (conj (get-in @a k) v)))
      (swap! a assoc k v))
    v))

(defn calc
  ([] (calc {}))
  ([stuff]
   (let [want (merge defaults stuff)
         re (atom {:unknown {:angles [] :points []} :errors []})
         sw (SwissEph.)
         jd (result re :jd (utc-to-jd (:utc want)))
         eph (result re :ephemeris :MOSEPH) ;; can become :SWIEPH or :JPLEPH
         flag (. SweConst SEFLG_SPEED)]
    (merge
      {:points
        (into {}
          (for [i (flatten [(:points want)])]
            (if (and (not (known? i)) (keyword? i))
              (do (result re [:unknown :points] i) {})
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
                                :sdd (aget res 3)}}))))}
      (if (and (geo? (:geo want))
               (or (angles? (:angles want))
                   (houses? (:houses want))))
        (let [cusps (double-array 13)
              ascmc (double-array 10)
              rc (.swe_houses sw
                              jd
                              0 ;; TODO: flags?
                              (:lat (:geo want))
                              (:lon (:geo want))
                              (int (result re :houses (coerce-houses (:houses want))))
                              cusps
                              ascmc)]
          (merge
            (nice-angles ascmc (:angles want) re)
            (nice-houses cusps (:houses want)))))
      (if (:meta want) {:wanted (dissoc want :meta)
                        :result @re})))))

(comment
  (calc {:points [:Sun :NA]})
  (calc {:utc "1974-06-30T21:45:00Z"
         :geo {:lat 43.217 :lon 27.917}
         :angles [:Asc :MC :NA]}))
