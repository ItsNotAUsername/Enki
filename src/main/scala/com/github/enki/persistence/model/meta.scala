package com.github.enki
package persistence
package model

import domain.Id

import doobie.{Get, Put}

object meta:

  given get4id[T]: Get[Id[T]] = Get[Long].temap(Id.from)
  given put4id[T]: Put[Id[T]] = Put[Long].contramap(Id.value)

end meta
