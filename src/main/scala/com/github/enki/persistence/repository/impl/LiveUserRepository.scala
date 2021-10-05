package com.github.enki
package persistence
package repository
package impl

import domain.{Email, Id}
import domain.user.User
import meta.given

import cats.effect.MonadCancelThrow
import cats.syntax.functor.*
import doobie.*
import doobie.implicits.*
import doobie.postgres.implicits.*
import java.util.UUID

class LiveUserRepository[F[_]: MonadCancelThrow](xa: Transactor[F]) extends UserRepository[F]:
  import query.UserQuery
  
  def createUser(user: User): F[User] =
    UserQuery.insertUser(user)
      .withUniqueGeneratedKeys[Id[User]]("id")
      .transact(xa)
      .map(userId => user.copy(id = userId))
  
  def updateUser(user: User): F[User] =
    UserQuery.updateUser(user)
      .run
      .transact(xa)
      .as(user)
  
  def findUserById(userId: Id[User]): F[Option[User]] =
    UserQuery.selectUserById(userId)
      .option
      .transact(xa)
  
  def findUserByEmail(email: Email): F[Option[User]] =
    UserQuery.selectUserByEmail(email)
      .option
      .transact(xa)

  def findUserByCode(code: UUID): F[Option[User]] =
    UserQuery.selectUserByCode(code)
      .option
      .transact(xa)

end LiveUserRepository
