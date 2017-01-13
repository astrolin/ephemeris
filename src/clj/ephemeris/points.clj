(ns ephemeris.points
  (:import (swisseph SweConst)))

(def ^:private dat (atom {}))
(def ^:private rev (atom {}))

(defn- a
  ([id idn] (a id idn nil))
  ([id idn greek]
   (do (swap! dat conj {id {:id idn
                            :en (name id)
                            :gr greek}})
       (swap! rev conj {idn id}))))

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

(defn- datv [id v] (v (id (deref dat))))

(defn known? [kwd]
  (if (keyword? kwd)
    (contains? dat kwd)
    false))

(defn lookup [what]
  (if (keyword? what)
    (datv what :id)
    (get (deref rev) what)))

(defn nameit
  ([key-id] (nameit key-id :gr))
  ([key-id lang] (let [res (datv key-id lang)]
                   (if (nil? res)
                     (datv key-id :en)
                     res))))
