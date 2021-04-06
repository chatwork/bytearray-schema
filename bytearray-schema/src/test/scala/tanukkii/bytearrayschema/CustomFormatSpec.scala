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

package tanukkii.bytearrayschema

import org.scalatest.funsuite.AnyFunSuite

class CustomFormatSpec extends AnyFunSuite {

  import MyTypeProtocol._

  test("convertTo") {
    assert(Map("name" -> "John Doe".getBytes, "value" -> 10.toString.getBytes).convertTo[MyType] === MyType("John Doe", 10))
  }

  test("convertTo from mutable.Map") {
    assert(scala.collection.mutable.Map("name" -> "John Doe".getBytes, "value" -> 10.toString.getBytes).convertTo[MyType] === MyType("John Doe", 10))
  }

  test("not convert empty BytesMap") {
    val errorMessage = try {
      Map.empty[String, Array[Byte]].convertTo[MyType]
    } catch {
      case e: EmptyBytesMapException => e.getMessage
    }
    assert(errorMessage === "BytesMap is empty. It should contain fields: name, value")
  }

  test("toBytesMap") {
    assert(MyType("John Doe", 10).toBytesMap.mapValues(_.toVector).toMap === Map("name" -> "John Doe".getBytes().toVector, "value" -> 10.toString.getBytes().toVector))
  }

  test("convertTo Some") {
    assert(Map(
      "name" -> "John Doe".getBytes,
      "value" -> 10.toString.getBytes,
      "point" -> 1.0.toString.getBytes
    ).convertTo[OptType] === OptType("John Doe", 10, Some(1.0)))
  }

  test("convertTo None") {
    assert(Map(
      "name" -> "John Doe".getBytes,
      "value" -> 10.toString.getBytes
    ).convertTo[OptType] === OptType("John Doe", 10, None))
  }

  test("convertTo None 2") {
    assert(Map(
      "name" -> "John Doe".getBytes,
      "value" -> 10.toString.getBytes,
      "point" -> Array.empty[Byte]
    ).convertTo[OptType] === OptType("John Doe", 10, None))
  }

  test("convertTo None 3") {
    assert(Map(
      "name" -> "John Doe".getBytes,
      "value" -> 10.toString.getBytes
    ).convertTo[OptType] === OptType("John Doe", 10, None))
  }

  test("toBytesMap Some") {
    assert(OptType("John Doe", 10, Some(1.0)).toBytesMap.mapValues(_.toVector).toMap === Map(
      "name" -> "John Doe".getBytes.toVector,
      "value" -> 10.toString.getBytes.toVector,
      "point" -> 1.0.toString.getBytes.toVector
    ))
  }

  test("toBytesMap None") {
    assert(OptType("John Doe", 10, None).toBytesMap.mapValues(_.toVector).toMap === Map(
      "name" -> "John Doe".getBytes.toVector,
      "value" -> 10.toString.getBytes.toVector
    ))
  }

}

case class MyType(name: String, value: Int)

case class OptType(name: String, value: Int, point: Option[Double])

object MyTypeProtocol extends DefaultBytesMapProtocol {

  implicit val IntByteArrayFormat: ByteArrayFormat[Int] = new ByteArrayFormat[Int] {
    override def write(obj: Int): Array[Byte] = obj.toString.getBytes

    override def read(bytes: Array[Byte]): Int = new String(bytes, 0, bytes.length).toInt
  }
  implicit val ByteByteArrayFormat: ByteArrayFormat[Byte] = new ByteArrayFormat[Byte] {
    def write(obj: Byte): Array[Byte] = obj.toString.getBytes

    def read(bytes: Array[Byte]): Byte = new String(bytes, 0, bytes.length).toByte
  }
  implicit val BooleanByteArrayFormat: ByteArrayFormat[Boolean] = new ByteArrayFormat[Boolean] {
    def write(obj: Boolean): Array[Byte] = obj.toString.getBytes

    def read(bytes: Array[Byte]): Boolean = new String(bytes, 0, bytes.length).toBoolean
  }
  implicit val UnitByteArrayFormat: ByteArrayFormat[Unit] = new ByteArrayFormat[Unit] {
    override def write(obj: Unit): Array[Byte] = obj.toString.getBytes

    override def read(bytes: Array[Byte]): Unit = {}
  }
  implicit val StringByteArrayFormat: ByteArrayFormat[String] = new ByteArrayFormat[String] {
    override def write(obj: String): Array[Byte] = obj.getBytes

    override def read(bytes: Array[Byte]): String = new String(bytes, 0, bytes.length)
  }
  implicit val CharByteArrayFormat: ByteArrayFormat[Char] = new ByteArrayFormat[Char] {
    override def write(obj: Char): Array[Byte] = obj.toString.getBytes

    override def read(bytes: Array[Byte]): Char = new String(bytes, 0, bytes.length).charAt(0)
  }
  implicit val ShortByteArrayFormat: ByteArrayFormat[Short] = new ByteArrayFormat[Short] {
    override def write(obj: Short): Array[Byte] = obj.toString.getBytes

    override def read(bytes: Array[Byte]): Short = new String(bytes, 0, bytes.length).toShort
  }
  implicit val SymbolByteArrayFormat: ByteArrayFormat[Symbol] = new ByteArrayFormat[Symbol] {
    override def write(obj: Symbol): Array[Byte] = obj.toString.getBytes

    override def read(bytes: Array[Byte]): Symbol = Symbol(new String(bytes, 0, bytes.length))
  }
  implicit val FloatByteArrayFormat: ByteArrayFormat[Float] = new ByteArrayFormat[Float] {
    override def write(obj: Float): Array[Byte] = obj.toString.getBytes

    override def read(bytes: Array[Byte]): Float = new String(bytes, 0, bytes.length).toFloat
  }
  implicit val BigDecimalByteArrayFormat: ByteArrayFormat[BigDecimal] = new ByteArrayFormat[BigDecimal] {
    override def write(obj: BigDecimal): Array[Byte] = obj.toString.getBytes

    override def read(bytes: Array[Byte]): BigDecimal = BigDecimal(new String(bytes, 0, bytes.length))
  }
  implicit val DoubleByteArrayFormat: ByteArrayFormat[Double] = new ByteArrayFormat[Double] {
    override def write(obj: Double): Array[Byte] = obj.toString.getBytes

    override def read(bytes: Array[Byte]): Double = new String(bytes, 0, bytes.length).toDouble
  }
  implicit val LongByteArrayFormat: ByteArrayFormat[Long] = new ByteArrayFormat[Long] {
    override def write(obj: Long): Array[Byte] = obj.toString.getBytes

    override def read(bytes: Array[Byte]): Long = new String(bytes, 0, bytes.length).toLong
  }

  implicit val myTypeByteArrayFormat: BytesMapFormat[MyType] = bytesMapFormat2(MyType)

  implicit val optTypeByteArrayFormat: BytesMapFormat[OptType] = bytesMapFormat3(OptType)
}
