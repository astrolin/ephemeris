# ephemeris [![Build Status](https://circleci.com/gh/astrolet/ephemeris/tree/active.svg?style=shield&circle-token=201676e3ede03d8bb18e92648dee447450ea5891)](https://circleci.com/gh/astrolet/ephemeris/tree/active)

> Swiss Ephemeris for Clojure

This library is based on the Swiss Ephemeris port to Java by Thomas Mack.
The source code of v[2.01.00-01](http://th-mack.de/international/download)
is included, compiled and packaged.  I guess any JVM-based language
can use this ephemeris if there is interest.

## Required

- Java / JDK `1.6`, `1.7`, or `1.8`
- [Leiningen](https://leiningen.org)

## Use

[![Clojars Project](http://clojars.org/ephemeris/latest-version.svg)](http://clojars.org/ephemeris)

### Clojure

```sh
lein repl
```

There is a single `calc` function in `ephemeris.core` that does it all.

```clojure
(calc) ;; give it a map of what you'd like to get - merged into the defaults
```

Here are the current `defaults` as a template to override with what's wanted:

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
You can give it a `'string'` map, in `edn` data format, see the defaults above.

It's full-featured, though also slow due to JVM startup time.

```sh
bin/ephemeris
bin/ephemeris '{}' # produces same result as the above
```

## Test

Run `lein test` or better `lein autotest`.

## Lacks

The following features are still missing.
Some will be added soon and others later.

- Error Handling
- Precision `:ephemeris` option `:SWIEPH` or `:JPLEPH`
- Precession `:siderial true` or specify `:ayanamsha`
- Schema validation for `calc` parameter
- Fixed Stars
- Prenatal Lunation
- Testimonies

Anything else so far needed for practice of astrology,
I believe can be derived without need of an ephemeris.

## License

[GPL v2+](http://www.gnu.org/licenses/old-licenses/gpl-2.0.html) or
[Swiss Ephemeris](http://www.astro.com/swisseph)
