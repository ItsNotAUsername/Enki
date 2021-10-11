package com.github.enki
package persistence
package query

import domain.{Id, Pagination, RoleName}
import domain.permission.{Permission, Role, Scope}
import domain.workspace.Workspace
import filter.RoleFilter
import model.RoleRow
import model.meta.given

import cats.data.NonEmptyList as NEL
import doobie.{Query0, Update0}
import doobie.implicits.*
import doobie.util.fragments.{in, whereAnd}

private[persistence] object RoleQuery:

  private val selectRoleFragment = fr"""
    SELECT r.id, r.name, r.system, r.workspace_id
      FROM role r
  """

  def selectById(roleId: Id[Role]): Query0[RoleRow] =
    (selectRoleFragment ++ fr"WHERE r.id = $roleId")
      .query[RoleRow]

  def selectByIds(roleIds: NEL[Id[Role]]): Query0[RoleRow] =
    (selectRoleFragment ++ whereAnd(in(fr"r.id", roleIds)))
      .query[RoleRow]

  def selectByFilter(filter: RoleFilter, pagination: Pagination): Query0[RoleRow] =
    SqlPagination
      .paginate[RoleRow](selectRoleFragment ++ filter.fragment, pagination)

  def insert(roleRow: RoleRow): Update0 =
    sql"""
      INSERT INTO role (name, system, workspace_id)
           VALUES (${roleRow.name}, 
                   ${roleRow.system}, 
                   ${roleRow.workspace})
    """.update

  def update(roleRow: RoleRow): Update0 =
    sql"""
      UPDATE role
         SET name = ${roleRow.name}
       WHERE id   = ${roleRow.id}
    """.update

  def delete(roleId: Id[Role]): Update0 =
    sql"DELETE FROM role r WHERE r.id = $roleId"
      .update

end RoleQuery
