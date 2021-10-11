package com.github.enki
package persistence
package query

import domain.Id
import domain.permission.{Permission, Role}
import meta.given
import model.RolePermissionRow

import cats.data.NonEmptyList as NEL
import doobie.{Query0, Update0, Update}
import doobie.implicits.*
import doobie.util.fragments.{in, whereAnd}

private[persistence] object RolePermissionQuery:

  private val selectFragment = fr"""
    SELECT rp.role_id, rp.permission_id
      FROM role_permission rp
  """

  def selectByRoleId(roleId: Id[Role]): Query0[RolePermissionRow] =
    (selectFragment ++ fr"WHERE rp.role_id = $roleId")
      .query[RolePermissionRow]

  def selectByRoleIds(roleIds: NEL[Id[Role]]): Query0[RolePermissionRow] =
    (selectFragment ++ whereAnd(in(fr"rp.role_id", roleIds)))
      .query[RolePermissionRow]

  def insert: Update[RolePermissionRow] =
    val sql = "INSERT INTO role_permission (role_id, permission_id) VALUES (?, ?)"
    Update[RolePermissionRow](sql)

  def deleteByRoleId(roleId: Id[Role]): Update0 =
    sql"DELETE FROM role_permission rp WHERE rp.role_id = $roleId"
      .update

end RolePermissionQuery
