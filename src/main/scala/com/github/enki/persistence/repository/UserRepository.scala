package com.github.enki
package persistence
package repository

import domain.Id
import domain.user.*

import java.util.UUID

trait UserRepository[F[_]]:
  def createUser(user: CreateUser): F[User]
  def updateUser(userId: Id, user: UpdateUser): F[Unit]
  def findUserById(userId: Id): F[Option[User]]
  def findUserByEmail(email: String): F[Option[User]]
  def findUserByCode(code: UUID): F[Option[User]]
