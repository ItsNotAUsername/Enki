package com.github.enki
package util
package newtype

import doobie.Meta
import io.circe.{Decoder, Encoder}
import pureconfig.ConfigReader
import sttp.tapir.Codec.PlainCodec as TapirCodec

abstract class WrappedNewtype[T] extends WrappedNewtypeInstances[T]:
  opaque type Type = T

  extension (t: Type)
    def value: T = t

  def apply(t: T): Type = t

end WrappedNewtype

// Given instances
private trait WrappedNewtypeInstances[T]:
  self: WrappedNewtype[T] =>

  given meta4newtype(using underlying: Meta[T]): Meta[self.Type] =
    underlying.timap(apply)(value)

  given configReader4newtype(using underlying: ConfigReader[T]): ConfigReader[Type] =
    underlying.map(apply)

  given circeDecoder4newtype(using underlying: Decoder[T]): Decoder[Type] =
    underlying.map(apply)

  given circeEncoder4newtype(using underlying: Encoder[T]): Encoder[Type] =
    underlying.contramap(value)

  given tapirCodec4newtype(using underlying: TapirCodec[T]): TapirCodec[Type] =
    underlying.map(apply)(value)

end WrappedNewtypeInstances
