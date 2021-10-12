package com.github.enki
package util
package db

import cats.data.NonEmptyList
import cats.syntax.list.*
import doobie.{ConnectionIO, Query0}

object query:
  
  extension [T](query: Query0[T])
    def toNelOption: ConnectionIO[Option[NonEmptyList[T]]] = 
      query.to[List].map(_.toNel)

end query
