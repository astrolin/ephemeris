# ephemeris [![Build Status](https://circleci.com/gh/astrolin/ephemeris/tree/active.svg?style=shield)](https://circleci.com/gh/astrolin/ephemeris/tree/active)

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

Without geo-location coordinates it returns the current (i.e. mundane) planet positions.

The time parameter `:utc` can be a string in
[ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) or
[RFC 3339](https://tools.ietf.org/html/rfc3339#section-5.6) format
which include ability to specify timezone.

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

- Accept `:geo` location in more formats:
  * [ISO 6709](https://en.wikipedia.org/wiki/ISO_6709) string
  * Vector of numbers `[43.22 27.92]` lat & lon -- are fixed in that order
  * Vector of strings `["43n13" "27e55"]` oldschool -- lat & lon in either order (parse & check: 'N', 'E', 'S', 'W')
- Error Handling
- Precision `:ephemeris` option `:SWIEPH` or `:JPLEPH`
- Precession `:siderial true` or specify `:ayanamsha`
- Schema validation for `calc` parameter
- House :System keywords would be better
- Fixed Stars
- Prenatal Lunation
- Testimonies

Anything else so far needed for practice of astrology,
I believe can be derived without need of an ephemeris.

## License

[GPL v2+](http://www.gnu.org/licenses/old-licenses/gpl-2.0.html) or
[Swiss Ephemeris](http://www.astro.com/swisseph)
