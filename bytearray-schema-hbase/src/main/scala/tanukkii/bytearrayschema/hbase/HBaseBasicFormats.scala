/*
 * Original implementation (C) 2009-2011 Debasish Ghosh
 * Adapted and extended in 2011 by Mathias Doenitz
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
package hbase

import org.apache.hadoop.hbase.util.Bytes

/**
  * Provides the ByteArrayFormats for the most important Scala types.
  */
trait HBaseBasicFormats extends BasicFormats {

  implicit object IntByteArrayFormat extends ByteArrayFormat[Int] {
    def write(x: Int): Array[Byte] = Bytes.toBytes(x)
    def read(value: Array[Byte]): Int = Bytes.toInt(value)
  }

  implicit object LongByteArrayFormat extends ByteArrayFormat[Long] {
    def write(x: Long): Array[Byte] = Bytes.toBytes(x)
    def read(value: Array[Byte]): Long = Bytes.toLong(value)
  }

  implicit object FloatByteArrayFormat extends ByteArrayFormat[Float] {
    def write(x: Float): Array[Byte] = Bytes.toBytes(x)
    def read(value: Array[Byte]): Float = Bytes.toFloat(value)
  }

  implicit object DoubleByteArrayFormat extends ByteArrayFormat[Double] {
    def write(x: Double): Array[Byte] = Bytes.toBytes(x)
    def read(value: Array[Byte]): Double = Bytes.toDouble(value)
  }

  implicit object ByteByteArrayFormat extends ByteArrayFormat[Byte] {
    def write(x: Byte): Array[Byte] = Bytes.toBytes(x)
    def read(value: Array[Byte]): Byte = if (value.length == 1) value.head else deserializationError("Expected one Byte")
  }

  implicit object ShortByteArrayFormat extends ByteArrayFormat[Short] {
    def write(x: Short): Array[Byte] = Bytes.toBytes(x)
    def read(value: Array[Byte]): Short = Bytes.toShort(value)
  }

  implicit object BigDecimalByteArrayFormat extends ByteArrayFormat[BigDecimal] {
    def write(x: BigDecimal): Array[Byte] = {
      require(x ne null)
      Bytes.toBytes(x.underlying())
    }
    def read(value: Array[Byte]): BigDecimal = Bytes.toBigDecimal(value)
  }

  implicit object UnitByteArrayFormat extends ByteArrayFormat[Unit] {
    def write(x: Unit): Array[Byte] = Bytes.toBytes(1)
    def read(value: Array[Byte]): Unit = ()
  }

  implicit object BooleanByteArrayFormat extends ByteArrayFormat[Boolean] {
    def write(x: Boolean): Array[Byte] = Bytes.toBytes(x)
    def read(value: Array[Byte]): Boolean = Bytes.toBoolean(value)
  }

  implicit object CharByteArrayFormat extends ByteArrayFormat[Char] {
    def write(x: Char): Array[Byte] = Bytes.toBytes(x)
    def read(value: Array[Byte]): Char = {
      val s = Bytes.toString(value)
      if (s.length == 1) s.charAt(0)
      else deserializationError("Expected Char")
    }
  }

  implicit object StringByteArrayFormat extends ByteArrayFormat[String] {
    def write(x: String): Array[Byte] = {
      require(x ne null)
      Bytes.toBytes(x)
    }
    def read(value: Array[Byte]): String = Bytes.toString(value)
  }

  implicit object SymbolByteArrayFormat extends ByteArrayFormat[Symbol] {
    def write(x: Symbol): Array[Byte] = Bytes.toBytes(x.name)
    def read(value: Array[Byte]): Symbol = Symbol(Bytes.toString(value))
  }
}
