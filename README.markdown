# bytearray-schema

Schemaless byte-array complex from/to Scala case class converter.

[![CircleCI](https://circleci.com/gh/chatwork/bytearray-schema.svg?style=svg)](https://circleci.com/gh/chatwork/bytearray-schema)

## Install

```sbt
libraryDependencies ++= Seq(
  "com.chatwork" %% "bytearray-schema" % "1.0.0",
  "com.chatwork" %% "bytearray-schema-hbase" % "1.0.0",
)
```

## Usage

Note) package name starts with `tanukki` since this is a fork of https://github.com/TanUkkii007/bytearray-schema

```scala
import tanukkii.bytearrayschema.BytesMapFormat
import tanukkii.bytearrayschema.hbase.HBaseBytesMapProtocol

case class HMessageBody(id: Long, message: String, created_at: Long)

object HBaseMessageBodyBytesMapProtocol extends HBaseBytesMapProtocol {
  implicit val HBaseMessageBodyFormat: BytesMapFormat[HBaseMessageBody] = bytesMapFormat8(HBaseMessageBody)
}
```
