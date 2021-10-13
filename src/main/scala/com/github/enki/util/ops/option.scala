package com.github.enki
package util
package ops

import cats.Applicative
import cats.syntax.all.*

object option:

  extension [A](opt: Option[A])
    def applyOrNone[F[_]: Applicative, B](f: A => F[Option[B]]): F[Option[B]] =
      opt.fold(none[B].pure[F])(f)

end option
