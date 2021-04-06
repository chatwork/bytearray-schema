import spray.boilerplate.BoilerplatePlugin

val scala211Version = "2.11.8"
val scala212Version = "2.12.2"

lazy val root = (project in file("."))
  .settings(
    name := "bytearray-schema",
    publish := {},
    Compile / publishArtifact := false,
    releaseCrossBuild := true,
    crossScalaVersions := Seq(scala211Version, scala212Version)
  ).aggregate(bytearraySchema, bytearraySchemaHBase)

val commonSettings = Seq(
  organization := "github.com/TanUkkii007",
  description := "schemaless byte array complex to Scala type coversion",
  homepage := Some(new URL("https://github.com/TanUkkii007/bytearray-schema")),
  startYear := Some(2016),
  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq(scala211Version, scala212Version),
  releaseCrossBuild := true,
  scalacOptions ++= Seq("-feature", "-language:_", "-unchecked", "-deprecation", "-encoding", "utf8"),
  resolvers += Opts.resolver.sonatypeReleases,
)

lazy val bytearraySchema = (project in file("bytearray-schema")).settings(
  commonSettings ++ Seq(
    name := "bytearray-schema",
    libraryDependencies ++= Seq(
      "org.specs2" %% "specs2-core" % "3.9.1" % "test",
      "org.specs2" %% "specs2-scalacheck" % "3.9.1" % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
    )
  )
).enablePlugins(BoilerplatePlugin)

lazy val bytearraySchemaHBase = (project in file("bytearray-schema-hbase")).settings(
  name := "bytearray-schema-hbase",
  commonSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.apache.hadoop" % "hadoop-common" % "2.6.2" % "provided",
      "org.apache.hbase" % "hbase-common" % "1.1.4" % "provided",
      "org.specs2" %% "specs2-core" % "3.9.1" % "test"
    )
  )
).dependsOn(bytearraySchema)
