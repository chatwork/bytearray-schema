lazy val root = (project in file("."))
  .settings(
    publish := (),
    publishArtifact in Compile := false
  ).aggregate(bytearraySchema, bytearraySchemaHBase)

val commonSettings = Seq(
  organization := "github.com/TanUkkii007",
  description := "schemaless byte array complex to Scala type coversion",
  homepage := Some(new URL("https://github.com/TanUkkii007/bytearray-schema")),
  startYear := Some(2016),
  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.10.6", "2.11.8"),
  releaseCrossBuild := true,
  scalacOptions ++= Seq("-feature", "-language:_", "-unchecked", "-deprecation", "-encoding", "utf8"),
  resolvers += Opts.resolver.sonatypeReleases,
  (scalacOptions in doc) ++= Seq("-doc-title", name.value + " " + version.value)
)

lazy val bytearraySchema = (project in file("bytearray-schema")).settings(
  commonSettings ++ Seq(
    name := "bytearray-schema",
    libraryDependencies ++= Seq(
      "org.specs2" %% "specs2-core" % "3.8.4" % "test",
      "org.specs2" %% "specs2-scalacheck" % "3.8.4" % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.1" % "test"
    ),
    BintrayPlugin.autoImport.bintrayPackage := "bytearray-schema"
  )
).settings(Boilerplate.settings).enablePlugins(BintrayPlugin)

lazy val bytearraySchemaHBase = (project in file("bytearray-schema-hbase")).settings(
  name := "bytearray-schema-hbase",
  commonSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.apache.hadoop" % "hadoop-common" % "2.6.2" % "provided",
      "org.apache.hbase" % "hbase-common" % "1.1.4" % "provided",
      "org.specs2" %% "specs2-core" % "3.8.4" % "test"
    ),
    BintrayPlugin.autoImport.bintrayPackage := "bytearray-schema-hbase"
  )
).dependsOn(bytearraySchema).enablePlugins(BintrayPlugin)