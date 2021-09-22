package com.github.enki
package domain

import doobie.{Get, Put}

final case class Id private(value: Long) extends AnyVal

object Id:

  private val errorMsg = "Id must be positive integer!"

  def apply(value: Long): Id = unsafeFrom(value)

  def from(value: Long): Either[String, Id] =
    Either.cond(value > 0, new Id(value), errorMsg)

  def unsafeFrom(value: Long): Id = 
    if value > 0 then new Id(value)
    else throw new IllegalArgumentException(errorMsg)

  given get4id: Get[Id] = Get[Long].temap(from)
  given put4id: Put[Id] = Put[Long].tcontramap(_.value)

end Id
