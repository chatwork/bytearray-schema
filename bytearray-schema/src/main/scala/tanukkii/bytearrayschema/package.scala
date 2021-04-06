/*
 * Copyright (C) 2009-2011 Mathias Doenitz
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

package tanukkii

package object bytearrayschema {

  type ByteArrayField = (String, Array[Byte])

  def deserializationError(msg: String, cause: Throwable = null, fieldNames: List[String] = Nil) = throw new DeserializationException(msg, cause, fieldNames)
  def serializationError(msg: String) = throw new SerializationException(msg)

  def bytesMapReader[T](implicit reader: BytesMapReader[T]): BytesMapReader[T] = reader
  def bytesMapWriter[T](implicit writer: BytesMapWriter[T]): BytesMapWriter[T] = writer

  implicit final class AnyOps[T](private val any: T) extends AnyVal {
    def toBytes(implicit writer: ByteArrayWriter[T]): Array[Byte] = writer.write(any)

    def toBytesMap(implicit writer: BytesMapWriter[T]): Map[String, Array[Byte]] = writer.write(any)
  }

  implicit final class MapOps(private val map: Map[String, Array[Byte]]) extends AnyVal {
    def convertTo[T](implicit reader: BytesMapReader[T]): T = reader.read(map)
  }

  implicit final class MutableMapOps(private val map: scala.collection.mutable.Map[String, Array[Byte]]) extends AnyVal {
    def convertTo[T](implicit reader: MutableBytesMapReader[T]): T = reader.readMutable(map)
  }
}

package bytearrayschema {

  case class DeserializationException(msg: String, cause: Throwable = null, fieldNames: List[String] = Nil) extends RuntimeException(msg, cause)
  class SerializationException(msg: String) extends RuntimeException(msg)
  class EmptyBytesMapException(fieldNames: List[String]) extends RuntimeException(s"BytesMap is empty. It should contain fields: ${fieldNames.mkString(", ")}")
}
