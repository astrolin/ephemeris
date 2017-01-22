(ns ephemeris.points-test
  (:require [midje.sweet :refer :all]
            [ephemeris.points :as ps]))

(facts "points"
  (ps/lookup :Sun) => 0
  (ps/lookup 0) => :Sun
  (ps/lookup :Unknown) => nil
  (ps/known? :Sun) => true
  (ps/known? :Unknown) => false
  (ps/nameit :Sun) => "Helios"
  (ps/nameit :Sun :en) => "Sun")
