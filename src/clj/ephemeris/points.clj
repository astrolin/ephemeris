(ns ephemeris.points
  (:import (swisseph SweConst)))

(def ^:private db (atom {}))

(defn- a
  ([id idn] (a id idn nil))
  ([id idn greek] (swap! db conj {id {:id idn
                                      :en (name id)
                                      :gr greek}})))

(a :Sun (. SweConst SE_SUN) "Helios")
(a :Moon (. SweConst SE_MOON) "Selene")
(a :Mercury (. SweConst SE_MERCURY) "Hermes")
(a :Venus (. SweConst SE_VENUS) "Aphrodite")
(a :Mars (. SweConst SE_MARS) "Ares")
(a :Jupiter (. SweConst SE_JUPITER) "Zeus")
(a :Saturn (. SweConst SE_SATURN) "Kronos")
(a :Uranus (. SweConst SE_URANUS))
(a :Neptune (. SweConst SE_NEPTUNE))
(a :Pluto (. SweConst SE_PLUTO))

(deref db)

(defn lookup [what]
  (get (get (deref db) what) :id))
