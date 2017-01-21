# ephemeris

> Swiss Ephemeris for Clojure

## USE

### Clojure

```sh
lein repl
```

There is a single `calc` function in `ephemeris.core` that does it all.

Here are the defaults it uses:

```edn
{:utc nil
 :geo {:lat nil :lon nil}
 :points [:Sun :Moon :Mercury :Venus :Mars :Jupiter :Saturn
          :TrueNode :Uranus :Neptune :Pluto]
 :angles [:Asc :MC]
 :houses "O"
 :meta true}
```

In `ephemeris.core`, the default namespace:

```clojure
(calc) ;; give it a map, merged into the defaults above
```

### CLI

This is just for testing purposes.  You can provide a `'string'` argument.
It's full-featured, though also slow due to JVM warm-up time.

```sh
bin/ephemeris
```

## TEST

```sh
lein test
```

## LICENSE

[GPL v2+](http://www.gnu.org/licenses/old-licenses/gpl-2.0.html) or
[Swiss Ephemeris](http://www.astro.com/swisseph)
