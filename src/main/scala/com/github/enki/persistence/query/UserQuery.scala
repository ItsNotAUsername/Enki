package com.github.enki
package persistence
package query

import domain.{Email, Id}
import domain.user.User
import meta.given

import doobie.*
import doobie.implicits.*
import doobie.postgres.implicits.*
import java.util.UUID

private[persistence] object UserQuery:

  def selectUserById(id: Id[User]): Query0[User] =
    sql"""
      SELECT u.id, u.username, u.email, u.password, u.active, u.code, u.created, u.updated
        FROM usr u
       WHERE u.id = $id
    """.query[User]

  def selectUserByEmail(email: Email): Query0[User] =
    sql"""
      SELECT u.id, u.username, u.email, u.password, u.active, u.code, u.created, u.updated
        FROM usr u
       WHERE u.email = $email
    """.query[User]

  def selectUserByCode(code: UUID): Query0[User] =
    sql"""
      SELECT u.id, u.username, u.email, u.password, u.active, u.code, u.created, u.updated
        FROM usr u
       WHERE u.code = $code
    """.query[User]

  def insertUser(user: User): Update0 =
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

  def updateUser(user: User): Update0 =
    sql"""
      UPDATE usr
         SET username = ${user.username},
             email    = ${user.email},
             password = ${user.password},
             active   = ${user.active},
             code     = ${user.code},
             updated  = ${user.updated}
       WHERE id = ${user.id}
    """.update

end UserQuery
