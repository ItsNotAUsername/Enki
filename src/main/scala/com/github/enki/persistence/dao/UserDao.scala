package com.github.enki
package persistence
package dao

import domain.{Email, Id}
import domain.user.User

import doobie.ConnectionIO
import java.util.UUID

trait UserDao:
  def create(user: User): ConnectionIO[Id[User]]
  def update(user: User): ConnectionIO[Unit]
  def findById(id: Id[User]): ConnectionIO[Option[User]]
  def findByEmail(email: Email): ConnectionIO[Option[User]]
  def findByCode(code: UUID): ConnectionIO[Option[User]]
