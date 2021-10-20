package com.github.enki
package persistence
package dao
package live

import domain.{Email, Id}
import domain.user.User
import mapping.*

import cats.syntax.functor.*
import doobie.ConnectionIO
import doobie.implicits.*
import doobie.postgres.implicits.*
import java.util.UUID

class LiveUserDao extends UserDao:
  import query.UserQuery

  def create(user: User): ConnectionIO[Id[User]] =
     UserQuery
      .insert(user.toRow)
      .withUniqueGeneratedKeys[Id[User]]("id")

  def update(user: User): ConnectionIO[Unit] =
    UserQuery
      .update(user.toRow) 
      .run
      .void

  def findById(id: Id[User]): ConnectionIO[Option[User]] =
    UserQuery
      .selectById(id)
      .option
      .map(_.map(User.fromRow))

  def findByEmail(email: Email): ConnectionIO[Option[User]] =
    UserQuery
      .selectByEmail(email)
      .option
      .map(_.map(User.fromRow))

  def findByCode(code: UUID): ConnectionIO[Option[User]] =
    UserQuery
      .selectByCode(code)
      .option
      .map(_.map(User.fromRow))

end LiveUserDao
