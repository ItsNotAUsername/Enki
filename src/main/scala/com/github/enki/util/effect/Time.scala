package com.github.enki
package util
package effect

import cats.effect.Clock
import java.time.{LocalDate, LocalDateTime, ZoneOffset}

trait Time[F[_]]:
  def localDate: F[LocalDate]
  def localDateTime: F[LocalDateTime]

object Time:

  def apply[F[_]](using ev: Time[F]): Time[F] = ev

  given fromClock[F[_]](using C: Clock[F]): Time[F] with
    def localDate: F[LocalDate] = 
      C.applicative
        .map(C.realTimeInstant)(LocalDate.ofInstant(_, ZoneOffset.UTC))
    
    def localDateTime: F[LocalDateTime] = 
      C.applicative
        .map(C.realTimeInstant)(LocalDateTime.ofInstant(_, ZoneOffset.UTC))

end Time
