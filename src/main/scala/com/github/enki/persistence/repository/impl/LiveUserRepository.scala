package com.github.enki
package persistence
package repository
package impl

import domain.Id
import domain.user.*

import cats.effect.MonadCancelThrow
import cats.syntax.functor.*
import cats.syntax.option.*
import doobie.*
import doobie.implicits.*
import doobie.postgres.implicits.*
import java.util.UUID

class LiveUserRepository[F[_]: MonadCancelThrow](xa: Transactor[F]) extends UserRepository[F]:
  import query.UserQuery
  
  def createUser(user: CreateUser): F[User] =
    UserQuery.insertUser(user)
      .withUniqueGeneratedKeys[User](
        "id", "username", "email", "password", "active", "code", "created", "updated"
      )
      .transact(xa)
  
  def updateUser(userId: Id, user: UpdateUser): F[Unit] =
    UserQuery.updateUser(userId, user)
      .run
      .transact(xa)
      .void
  
  def findUserById(userId: Id): F[Option[User]] =
    UserQuery.selectUserById(userId)
      .option
      .transact(xa)
  
  def findUserByEmail(email: String): F[Option[User]] =
    UserQuery.selectUserByEmail(email)
      .option
      .transact(xa)

  def findUserByCode(code: UUID): F[Option[User]] =
    UserQuery.selectUserByCode(code)
      .option
      .transact(xa)

end LiveUserRepository
