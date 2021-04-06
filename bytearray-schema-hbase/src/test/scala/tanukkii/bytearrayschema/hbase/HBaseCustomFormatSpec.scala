/*
 * Copyright (C) 2011 Mathias Doenitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tanukkii.bytearrayschema.hbase

import org.apache.hadoop.hbase.util.Bytes
import org.scalatest.funsuite.AnyFunSuite

class HBaseCustomFormatSpec extends AnyFunSuite {

  import tanukkii.bytearrayschema._

  case class MyType(name: String, value: Int)

  case class OptType(name: String, value: Int, point: Option[Double])

  object MyTypeProtocol extends HBaseBytesMapProtocol {
    implicit val myTypeByteArrayFormat = bytesMapFormat2(MyType)

    implicit val optTypeByteArrayFormat = bytesMapFormat3(OptType)
  }

  import MyTypeProtocol._

  test("convertTo") {
    assert(Map("name" -> Bytes.toBytes("John Doe"), "value" -> Bytes.toBytes(10)).convertTo[MyType] === MyType("John Doe", 10))
  }

  test("convertTo from mutable.Map") {
    assert(scala.collection.mutable.Map("name" -> Bytes.toBytes("John Doe"), "value" -> Bytes.toBytes(10)).convertTo[MyType] === MyType("John Doe", 10))
  }

  test("toBytesMap") {
    assert(MyType("John Doe", 10).toBytesMap.mapValues(_.toVector).toMap === Map("name" -> Bytes.toBytes("John Doe").toVector, "value" -> Bytes.toBytes(10).toVector))
  }

  test("convertTo Some") {
    assert(Map(
      "name" -> Bytes.toBytes("John Doe"),
      "value" -> Bytes.toBytes(10),
      "point" -> Bytes.toBytes(1.0)
    ).convertTo[OptType] === OptType("John Doe", 10, Some(1.0)))
  }

  test("convertTo None") {
    assert(Map(
      "name" -> Bytes.toBytes("John Doe"),
      "value" -> Bytes.toBytes(10)
    ).convertTo[OptType] === OptType("John Doe", 10, None))
  }

  test("convertTo None 2") {
    assert(Map(
      "name" -> Bytes.toBytes("John Doe"),
      "value" -> Bytes.toBytes(10),
      "point" -> Array.empty[Byte]
    ).convertTo[OptType] === OptType("John Doe", 10, None))
  }

  test("convertTo None 3") {
    assert(Map(
      "name" -> Bytes.toBytes("John Doe"),
      "value" -> Bytes.toBytes(10)
    ).convertTo[OptType] === OptType("John Doe", 10, None))
  }

  test("toBytesMap Some") {
    assert(OptType("John Doe", 10, Some(1.0)).toBytesMap.mapValues(_.toVector).toMap === Map(
      "name" -> Bytes.toBytes("John Doe").toVector,
      "value" -> Bytes.toBytes(10).toVector,
      "point" -> Bytes.toBytes(1.0).toVector
    ))
  }

  test("toBytesMap None") {
    assert(OptType("John Doe", 10, None).toBytesMap.mapValues(_.toVector).toMap === Map(
      "name" -> Bytes.toBytes("John Doe").toVector,
      "value" -> Bytes.toBytes(10).toVector
    ))
  }

}
