lazy val root = (project in file(".")).aggregate(bytearraySchema, bytearraySchemaHBase)

val commonSettings = Seq(
  version := "0.0.1",
  organization := "github.com/TanUkkii007",
  description := "schemaless byte array complex to Scala type coversion",
  homepage := Some(new URL("https://github.com/TanUkkii007/bytearray-schema")),
  startYear := Some(2016),
  licenses := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-feature", "-language:_", "-unchecked", "-deprecation", "-encoding", "utf8"),
  resolvers += Opts.resolver.sonatypeReleases,
  (scalacOptions in doc) ++= Seq("-doc-title", name.value + " " + version.value)
)

lazy val bytearraySchema = (project in file("bytearray-schema")).settings(
  commonSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.specs2" %% "specs2-core" % "3.8.4" % "test",
      "org.specs2" %% "specs2-scalacheck" % "3.8.4" % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.1" % "test"
    )
  )
).settings(Boilerplate.settings)

lazy val bytearraySchemaHBase = (project in file("bytearray-schema-hbase")).settings(
  commonSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.apache.hadoop" % "hadoop-common" % "2.6.2" % "provided",
      "org.apache.hbase" % "hbase-common" % "1.1.4" % "provided",
      "org.specs2" %% "specs2-core" % "3.8.4" % "test"
    )
  )
).dependsOn(bytearraySchema)