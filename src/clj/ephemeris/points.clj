(ns ephemeris.points
  (:import (swisseph SwissEph SweConst)))

(def ^:private sw (SwissEph.))

(def ^:private db (transient {}))

(defn- a [id idx greek]
  (conj! db {id
                {:index idx
                 :name (.swe_get_planet_name sw idx)
                 :greek greek}}))

(a :Sun (. SweConst SE_SUN) "Helios")
(a :Moon (. SweConst SE_MOON) "Selene")
(a :Mercury (. SweConst SE_MERCURY) "Hermes")
(a :Venus (. SweConst SE_VENUS) "Aphrodite")
(a :Mars (. SweConst SE_MARS) "Ares")
(a :Jupiter (. SweConst SE_JUPITER) "Zeus")
(a :Saturn (. SweConst SE_SATURN) "Kronos")

(defn lookup [what]
  (get (get db what) :index))
