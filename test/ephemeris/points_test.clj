(ns ephemeris.points-test
  (:require [clojure.test :refer :all]
            [ephemeris.points :as ps]))

(deftest points-test
  (is (= 0 (ps/lookup :Sun)))
  (is (= :Sun (ps/lookup 0)))
  (is (nil? (ps/lookup :Unknown)))
  (is (true? (ps/known? :Sun)))
  (is (false? (ps/known? :Unknown)))
  (is (= "Helios" (ps/nameit :Sun)))
  (is (= "Sun" (ps/nameit :Sun :en))))
