package com.github.enki
package util
package newtype

import cats.Show
import cats.syntax.either.*
import doobie.{Get, Put}
import eu.timepit.refined.api.{Failed, Passed, Validate}
import io.circe.Codec as CirceCodec
import pureconfig.ConfigReader
import pureconfig.error.FailureReason
import sttp.tapir.DecodeResult
import sttp.tapir.Codec.PlainCodec as TapirCodec

abstract class RefinedNewtype[T, P](using validate: Validate[T, P]) extends RefinedNewtypeInstances[T, P]:
  opaque type Type = T

  extension (t: Type)
    def value: T = t

  def apply(t: T): Type = unsafeFrom(t)

  def from(t: T): Either[String, Type] =
    validate.validate(t) match
      case     Passed(_) => t.asRight[String]
      case f @ Failed(_) => validate.showResult(t, f).asLeft[Type]

  def unsafeFrom(t: T): Type =
    validate.validate(t) match
      case     Passed(_) => t
      case f @ Failed(_) => throw new IllegalArgumentException(validate.showResult(t, f))

end RefinedNewtype

// Given instances
private trait RefinedNewtypeInstances[T, P]:
  self: RefinedNewtype[T, P] =>

  given doobieGet4newtype(using underlying: Get[T], show: Show[T]): Get[Type] =
    underlying.temap(from)

  given doobiePut4newtype(using underlying: Put[T], show: Show[T]): Put[Type] =
    underlying.contramap(value)

  given configReader4newtype(using underlying: ConfigReader[T]): ConfigReader[Type] =
    underlying.emap { t => from(t).leftMap(s => new FailureReason { override def description = s } ) }

  given circeCodec4newtype(using underlying: CirceCodec[T]): CirceCodec[Type] =
    underlying.iemap(from)(value)

  given tapirCodec4newtype(using underlying: TapirCodec[T]): TapirCodec[Type] =
    underlying.mapDecode(raw => DecodeResult.fromEitherString(s"$raw", from(raw)))(value)
