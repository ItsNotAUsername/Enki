package com.github.enki
package domain

import cats.Order

type Id[+T] = Id.Type[T]

object Id:
  opaque type Type[+T] = Long

  extension [T](t: Type[T])
    def value: Long = t

  private val errorMsg: String = "Id must be positive integer"

  private def validate(value: Long): Boolean = value > 0

  val undefined: Type[Nothing] = 0

  def apply[T](value: Long): Type[T] = unsafeFrom(value)

  def from[T](value: Long): Either[String, Type[T]] =
    Either.cond(validate(value), value, errorMsg)

  def unsafeFrom[T](value: Long): Type[T] = 
    if validate(value) then value
    else throw new IllegalArgumentException(errorMsg)

  given order4id[T]: Order[Id[T]] = Order.fromLessThan[Long](_ < _)

end Id
