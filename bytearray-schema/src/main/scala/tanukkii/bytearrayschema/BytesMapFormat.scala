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

import scala.annotation.implicitNotFound

/**
 * Provides the BytesMap deserialization for type T.
 */
@implicitNotFound(msg = "Cannot find BytesMapReader or BytesMapFormat type class for ${T}")
trait BytesMapReader[T] {
  def read(bytesMap: Map[String, Array[Byte]]): T
}

object BytesMapReader {
  implicit def func2Reader[T](f: Map[String, Array[Byte]] => T): BytesMapReader[T] = new BytesMapReader[T] {
    def read(bytesMap: Map[String, Array[Byte]]) = f(bytesMap)
  }
}

@implicitNotFound(msg = "Cannot find MutableBytesMapReader or BytesMapFormat type class for ${T}")
trait MutableBytesMapReader[T] {
  def readMutable(bytesMap: scala.collection.mutable.Map[String, Array[Byte]]): T
}

object MutableBytesMapReader {
  implicit def func2Reader[T](f: scala.collection.mutable.Map[String, Array[Byte]] => T): MutableBytesMapReader[T] = new MutableBytesMapReader[T] {
    def readMutable(bytesMap: scala.collection.mutable.Map[String, Array[Byte]]) = f(bytesMap)
  }
}

/**
 * Provides the BytesMap serialization for type T.
 */
@implicitNotFound(msg = "Cannot find BytesMapWriter or BytesMapFormat type class for ${T}")
trait BytesMapWriter[T] {
  def write(obj: T): Map[String, Array[Byte]]
}

object BytesMapWriter {
  implicit def func2Writer[T](f: T => Map[String, Array[Byte]]): BytesMapWriter[T] = new BytesMapWriter[T] {
    def write(obj: T) = f(obj)
  }
}

/**
  * Provides the BytesMap deserialization and serialization for type T.
 */
trait BytesMapFormat[T] extends BytesMapReader[T] with BytesMapWriter[T] with MutableBytesMapReader[T]

trait ByteArrayReader[T] {
  def read(bytes: Array[Byte]): T
}

trait ByteArrayWriter[T] {
  def write(obj: T): Array[Byte]
}

trait ByteArrayFormat[T] extends ByteArrayReader[T] with ByteArrayWriter[T]