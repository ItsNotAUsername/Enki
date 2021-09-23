package com.github.enki
package persistence
package query

import domain.Id
import domain.user.*
import util.hkd.*

import doobie.*
import doobie.implicits.*
import doobie.postgres.implicits.*
import doobie.util.fragments.setOpt
import java.util.UUID

private[persistence] object UserQuery:

  def selectUserById(id: Id): Query0[User] =
    sql"""
      SELECT *
        FROM usr
       WHERE id = $id
    """.query[User]

  def selectUserByEmail(email: String): Query0[User] =
    sql"""
      SELECT *
        FROM usr
       WHERE email = $email
    """.query[User]

  def selectUserByCode(code: UUID): Query0[User] =
    sql"""
      SELECT *
        FROM usr
       WHERE code = $code
    """.query[User]

  def insertUser(user: CreateUser): Update0 =
    sql"""
      INSERT INTO usr (username, email, password, active, code, created, updated)
           VALUES (${user.username},
                   ${user.email},
                   ${user.password},
                   ${user.active},
                   ${user.code},
                   ${user.created},
                   ${user.updated})
    """.update

  def updateUser(userId: Id, user: UpdateUser): Update0 =
    val start = fr"UPDATE usr "
    val end   = fr" WHERE id = $userId"

    val fields = List(
      user.username.map(update => fr"username = ${update.set}"),
      user.email.map(update => fr"email = ${update.set}"),
      user.password.map(update => fr"password = ${update.set}"),
      user.active.map(update => fr"active = ${update.set}"),
      user.updated.map(update => fr"updated = ${update.set}"),
    )
    
    (start ++ setOpt(fields*) ++ end).update

end UserQuery
