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

import org.apache.hadoop.hbase.util.Bytes

/**
  * Provides the ByteArrayFormats for the most important Scala types.
 */
trait BasicFormats {

  implicit object IntByteArrayFormat extends ByteArrayFormat[Int] {
    def write(x: Int) = Bytes.toBytes(x)
    def read(value: Array[Byte]) = Bytes.toInt(value)
  }

  implicit object LongByteArrayFormat extends ByteArrayFormat[Long] {
    def write(x: Long) = Bytes.toBytes(x)
    def read(value: Array[Byte]) = Bytes.toLong(value)
  }

  implicit object FloatByteArrayFormat extends ByteArrayFormat[Float] {
    def write(x: Float) = Bytes.toBytes(x)
    def read(value: Array[Byte]) = Bytes.toFloat(value)
  }

  implicit object DoubleByteArrayFormat extends ByteArrayFormat[Double] {
    def write(x: Double) = Bytes.toBytes(x)
    def read(value: Array[Byte]) = Bytes.toDouble(value)
  }

  implicit object ByteByteArrayFormat extends ByteArrayFormat[Byte] {
    def write(x: Byte) = Bytes.toBytes(x)
    def read(value: Array[Byte]) = if (value.length == 1) value.head else deserializationError("Expected one Byte")
  }
  
  implicit object ShortByteArrayFormat extends ByteArrayFormat[Short] {
    def write(x: Short) = Bytes.toBytes(x)
    def read(value: Array[Byte]) = Bytes.toShort(value)
  }

  implicit object BigDecimalByteArrayFormat extends ByteArrayFormat[BigDecimal] {
    def write(x: BigDecimal) = {
      require(x ne null)
      Bytes.toBytes(x.underlying())
    }
    def read(value: Array[Byte]) = Bytes.toBigDecimal(value)
  }

  implicit object UnitByteArrayFormat extends ByteArrayFormat[Unit] {
    def write(x: Unit) = Bytes.toBytes(1)
    def read(value: Array[Byte]) {}
  }

  implicit object BooleanByteArrayFormat extends ByteArrayFormat[Boolean] {
    def write(x: Boolean) = Bytes.toBytes(x)
    def read(value: Array[Byte]) = Bytes.toBoolean(value)
  }

  implicit object CharByteArrayFormat extends ByteArrayFormat[Char] {
    def write(x: Char) = Bytes.toBytes(x)
    def read(value: Array[Byte]) = {
      val s = Bytes.toString(value)
      if (s.length == 1) s.charAt(0)
      else deserializationError("Expected Char")
    }
  }
  
  implicit object StringByteArrayFormat extends ByteArrayFormat[String] {
    def write(x: String) = {
      require(x ne null)
      Bytes.toBytes(x)
    }
    def read(value: Array[Byte]) = Bytes.toString(value)
  }
  
  implicit object SymbolByteArrayFormat extends ByteArrayFormat[Symbol] {
    def write(x: Symbol) = Bytes.toBytes(x.name)
    def read(value: Array[Byte]) = Symbol(Bytes.toString(value))
  }
}
