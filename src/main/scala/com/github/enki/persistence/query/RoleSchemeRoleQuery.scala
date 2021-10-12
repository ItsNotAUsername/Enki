package com.github.enki
package persistence
package query

import domain.Id
import domain.permission.{Role, RoleScheme}
import model.RoleSchemeRoleRow
import model.meta.given

import cats.data.NonEmptyList as NEL
import doobie.{Query0, Update0, Update}
import doobie.implicits.*
import doobie.util.fragments.{in, whereAnd}

private[persistence] object RoleSchemeRoleQuery:

  private val selectFragment = fr"""
    SELECT rsr.role_scheme_id, rsr.role_id
      FROM role_scheme_role rsr
  """

  def selectByRoleSchemeId(roleSchemeId: Id[RoleScheme]): Query0[RoleSchemeRoleRow] =
    (selectFragment ++ fr"WHERE rsr.role_scheme_id = $roleSchemeId")
      .query[RoleSchemeRoleRow]

  def selectByRoleSchemeIds(roleSchemeIds: NEL[Id[RoleScheme]]): Query0[RoleSchemeRoleRow] =
    (selectFragment ++ whereAnd(in(fr"rp.role_id", roleSchemeIds)))
      .query[RoleSchemeRoleRow]

  def insert: Update[RoleSchemeRoleRow] =
    val sql = "INSERT INTO role_scheme_role (role_scheme_id, role_id) VALUES (?, ?)"
    Update[RoleSchemeRoleRow](sql)

  def deleteByRoleSchemeId(id: Id[RoleScheme]): Update0 =
    sql"DELETE FROM role_scheme_role rsr WHERE rsr.role_scheme_id = $id"
      .update

end RoleSchemeRoleQuery
