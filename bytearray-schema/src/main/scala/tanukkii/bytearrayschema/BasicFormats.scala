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

/**
  * Provides the ByteArrayFormats for the most important Scala types.
 */
trait BasicFormats {

  implicit val IntByteArrayFormat: ByteArrayFormat[Int]

  implicit val LongByteArrayFormat: ByteArrayFormat[Long]

  implicit val FloatByteArrayFormat: ByteArrayFormat[Float]

  implicit val DoubleByteArrayFormat: ByteArrayFormat[Double]

  implicit val ByteByteArrayFormat: ByteArrayFormat[Byte]
  
  implicit val ShortByteArrayFormat: ByteArrayFormat[Short]

  implicit val BigDecimalByteArrayFormat: ByteArrayFormat[BigDecimal]

  implicit val UnitByteArrayFormat: ByteArrayFormat[Unit]

  implicit val BooleanByteArrayFormat: ByteArrayFormat[Boolean]

  implicit val CharByteArrayFormat: ByteArrayFormat[Char]
  
  implicit val StringByteArrayFormat: ByteArrayFormat[String]
  
  implicit val SymbolByteArrayFormat: ByteArrayFormat[Symbol]
}
