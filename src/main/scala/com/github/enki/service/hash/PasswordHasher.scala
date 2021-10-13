package com.github.enki
package service
package hash

import domain.{HashedPassword, PlainPassword}

import cats.effect.Sync
import cats.syntax.functor.*
import tsec.passwordhashers.PasswordHash
import tsec.passwordhashers.jca.SCrypt

trait PasswordHasher[F[_]]:
  def hash(password: PlainPassword): F[HashedPassword]
  def verify(password: PlainPassword, hash: HashedPassword): F[Boolean]

object PasswordHasher:

  def scrypt[F[_]: Sync]: PasswordHasher[F] = 
    new:
      def hash(password: PlainPassword): F[HashedPassword] =
        SCrypt.hashpw[F](password.value).map(HashedPassword.unsafeFrom)

      def verify(password: PlainPassword, hash: HashedPassword): F[Boolean] =
        SCrypt.checkpwBool[F](password.value, PasswordHash[SCrypt](hash.value))

end PasswordHasher
