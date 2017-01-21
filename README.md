# ephemeris

> Swiss Ephemeris for Clojure

This library is based on the Swiss Ephemeris port to Java by Thomas Mack.
The code of v[2.01.00-01](http://th-mack.de/international/download) is included.

## USE

### Clojure

```sh
lein repl
```

There is a single `calc` function in `ephemeris.core` that does it all.

```clojure
(calc) ;; give it a map of what you would like, merged into the defaults
```

Here are the defaults as a template to override:

```edn
{:utc nil
 :geo {:lat nil :lon nil}
 :points [:Sun :Moon :Mercury :Venus :Mars :Jupiter :Saturn
          :TrueNode :Uranus :Neptune :Pluto]
 :angles [:Asc :MC]
 :houses "O"
 :meta true}
```

### CLI

This is just for testing purposes.
You can give it a `'string'` arg, in edn format (see example above).

It's full-featured, though also slow due to JVM warm-up time.

```sh
bin/ephemeris '{}'
```

## TEST

Run `lein test` or better `lein autotest`.

## LACKS

The following features are still missing.
Some will be added soon and others later.

- Siderial
- Fixed Stars
- Prenatal Lunation
- Testimonies

## LICENSE

[GPL v2+](http://www.gnu.org/licenses/old-licenses/gpl-2.0.html) or
[Swiss Ephemeris](http://www.astro.com/swisseph)
