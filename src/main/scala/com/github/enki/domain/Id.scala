package com.github.enki
package domain

import doobie.{Get, Put}
import com.github.enki.domain.IdInstances

type Id[T] = Id.Type[T]

object Id extends IdInstances:
  opaque type Type[T] = Long

  extension [T](t: Type[T])
    def value: Long = t

  def undefined[T]: Type[T] = 0

  def apply[T](value: Long): Type[T] = unsafeFrom(value)

  def from[T](value: Long): Either[String, Type[T]] =
    Either.cond(validate(value), value, errorMsg)

  def unsafeFrom[T](value: Long): Type[T] = 
    if validate(value) then value
    else throw new IllegalArgumentException(errorMsg)

  private val errorMsg: String = "Id must be positive integer"

  private def validate(value: Long): Boolean = value > 0

end Id

private trait IdInstances:
  self: Id.type =>

  given doobieGet4id[T]: Get[Type[T]] = Get[Long].temap(from)

  given doobiePut4id[T]: Put[Type[T]] = Put[Long].tcontramap(value)

end IdInstances
