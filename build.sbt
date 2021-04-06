import spray.boilerplate.BoilerplatePlugin

val scala211Version = "2.11.8"
val scala212Version = "2.12.2"
val scala213Version = "2.13.5"

lazy val root = (project in file("."))
  .settings(
    name := "bytearray-schema",
    publish := {},
    Compile / publishArtifact := false,
    releaseCrossBuild := true,
    crossScalaVersions := Seq(scala211Version, scala212Version, scala213Version)
  ).aggregate(bytearraySchema, bytearraySchemaHBase)

val commonSettings = Seq(
  organization := "github.com/TanUkkii007",
  description := "schemaless byte array complex to Scala type coversion",
  homepage := Some(new URL("https://github.com/TanUkkii007/bytearray-schema")),
  startYear := Some(2016),
  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
  scalaVersion := scala213Version,
  crossScalaVersions := Seq(scala211Version, scala212Version, scala213Version),
  releaseCrossBuild := true,
  scalacOptions ++= Seq("-feature", "-language:_", "-unchecked", "-deprecation", "-encoding", "utf8"),
  resolvers += Opts.resolver.sonatypeReleases,
)

lazy val bytearraySchema = (project in file("bytearray-schema")).settings(
  commonSettings ++ Seq(
    name := "bytearray-schema",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest-funsuite" % Dependencies.scalatest % Test,
      "org.scalatestplus" %% "scalacheck-1-15" % s"${Dependencies.scalatest}.0" % Test,
      "org.scalacheck" %% "scalacheck" % "1.15.2" % Test
    )
  )
).enablePlugins(BoilerplatePlugin)

lazy val bytearraySchemaHBase = (project in file("bytearray-schema-hbase")).settings(
  name := "bytearray-schema-hbase",
  commonSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.apache.hadoop" % "hadoop-common" % "2.6.2" % "provided",
      "org.apache.hbase" % "hbase-common" % "1.1.4" % "provided",
      "org.scalatest" %% "scalatest-funsuite" % Dependencies.scalatest % Test
    )
  )
).dependsOn(bytearraySchema)
