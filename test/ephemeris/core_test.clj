(ns ephemeris.core-test
  (:require [midje.sweet :refer :all]
            [ephemeris.core :refer (calc)]))

(def geo-req {:utc "1974-06-30T21:45:00Z"
              :geo {:lat 43.217 :lon 27.917}})

(facts "given geo location"
  (fact "angles and houses"
    (let [res (calc geo-req)]
      (map? (get res :angles)) => true
      (map? (get res :houses)) => true)))

(facts "missing"
  (fact "points"
    (get-in (calc {:points [:Sun :Body]})
            [:result :unknown :points]) => [:Body])
  (fact "angles"
    (get-in (calc (assoc geo-req :angles [:Asc :MC :Angle]))
            [:result :unknown :angles]) => [:Angle]))
