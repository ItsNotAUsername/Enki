package com.github.enki
package util
package ops

import cats.Applicative
import cats.data.NonEmptyList
import cats.syntax.all.*

object list:

  extension [A](as: List[A])
    def applyOrNil[F[_]: Applicative, B](f: NonEmptyList[A] => F[List[B]]): F[List[B]] =
      as.toNel
        .fold(List.empty.pure[F])(f)

    def groupMapNel[K, B](key: A => K)(f: A => B): Map[K, NonEmptyList[B]] =
      as.groupMap(key)(f)
        .collect { case (k, b :: bs) => (k, NonEmptyList(b, bs)) }

end list
