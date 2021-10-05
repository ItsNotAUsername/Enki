package com.github.enki
package util
package newtype

import cats.Show
import cats.syntax.either.*
import doobie.{Get, Put}
import eu.timepit.refined.api.{Failed, Passed, Validate}

abstract class Newtype[T, P](using validate: Validate[T, P]):
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

  given get4newtype(using underlying: Get[T], show: Show[T]): Get[Type] =
    underlying.temap(from)

  given put4newtype(using underlying: Put[T], show: Show[T]): Put[Type] =
    underlying.contramap(value) 

end Newtype
