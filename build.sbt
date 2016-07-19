name := "bytearray-schema"

version := "0.0.1"

organization := "github.com/TanUkkii007"

description := "A Scala library for easy and idiomatic byte array key-value complex (de)serialization"

homepage := Some(new URL("https://github.com/TanUkkii007/bytearray-schema"))

startYear := Some(2016)

licenses := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-feature", "-language:_", "-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += Opts.resolver.sonatypeReleases

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-common" % "2.6.2",
  "org.apache.hbase" % "hbase-common" % "1.1.4",
  "org.specs2" %% "specs2-core" % "3.8.4" % "test",
  "org.specs2" %% "specs2-scalacheck" % "3.8.4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.1" % "test"
)

(scalacOptions in doc) ++= Seq("-doc-title", name.value + " " + version.value)

// generate boilerplate
Boilerplate.settings
