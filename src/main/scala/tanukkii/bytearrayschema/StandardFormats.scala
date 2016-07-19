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
  * Provides the ByteArrayFormats for the non-collection standard types.
 */
trait StandardFormats {

  private[bytearrayschema] type JF[T] = ByteArrayFormat[T] // simple alias for reduced verbosity

  implicit def optionFormat[T :JF](implicit reader: ByteArrayReader[T]): JF[Option[T]] = new OptionFormat[T]

  class OptionFormat[T :JF](implicit reader: ByteArrayReader[T]) extends JF[Option[T]] {
    def write(option: Option[T]) = option match {
      case Some(x) => x.toBytes
      case None => Array.empty
    }
    def read(value: Array[Byte]) = if (value.isEmpty) None else Some(reader.read(value))
    // allows reading the byte array as a Some (useful in container formats)
    def readSome(value: Array[Byte]) = Some(reader.read(value))
  }

  implicit def tuple1Format[A :JF](implicit reader: ByteArrayReader[A]) = new JF[Tuple1[A]] {
    def write(t: Tuple1[A]) = t._1.toBytes
    def read(value: Array[Byte]) = Tuple1(reader.read(value))
  }

}
