package com.github.enki
package persistence
package query

import domain.{Email, Id}
import domain.user.User
import meta.given
import model.UserRow

import doobie.{Query0, Update0}
import doobie.implicits.*
import doobie.postgres.implicits.*
import java.util.UUID

private[persistence] object UserQuery:

  private val selectFragment = 
    fr"""
      SELECT u.id, 
             u.username, 
             u.email, 
             u.password, 
             u.active, 
             u.code, 
             u.created, 
             u.updated
        FROM usr u
    """

  def selectById(id: Id[User]): Query0[UserRow] =
    (selectFragment ++ fr"WHERE u.id = $id")
      .query[UserRow]

  def selectByEmail(email: Email): Query0[UserRow] =
    (selectFragment ++ fr"WHERE u.email = $email")
      .query[UserRow]

  def selectByCode(code: UUID): Query0[UserRow] =
    (selectFragment ++ fr"WHERE u.code = $code")
      .query[UserRow]

  def insert(userRow: UserRow): Update0 =
    sql"""
      INSERT INTO usr (username, email, password, active, code, created, updated)
           VALUES (${userRow.username},
                   ${userRow.email},
                   ${userRow.password},
                   ${userRow.active},
                   ${userRow.code},
                   ${userRow.created},
                   ${userRow.updated})
    """.update

  def update(userRow: UserRow): Update0 =
    sql"""
      UPDATE usr
         SET username = ${userRow.username},
             email    = ${userRow.email},
             password = ${userRow.password},
             active   = ${userRow.active},
             code     = ${userRow.code},
             updated  = ${userRow.updated}
       WHERE id       = ${userRow.id}
    """.update

end UserQuery
