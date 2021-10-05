package com.github.enki
package persistence
package repository

import domain.{Email, Id}
import domain.user.User

import java.util.UUID

trait UserRepository[F[_]]:
  def createUser(user: User): F[User]
  def updateUser(user: User): F[User]
  def findUserById(userId: Id[User]): F[Option[User]]
  def findUserByEmail(email: Email): F[Option[User]]
  def findUserByCode(code: UUID): F[Option[User]]
