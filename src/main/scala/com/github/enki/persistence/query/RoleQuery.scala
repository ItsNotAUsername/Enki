package com.github.enki
package persistence
package query

import domain.{Id, Pagination, RoleName}
import domain.permission.{Permission, Role, Scope}
import domain.workspace.Workspace
import meta.given
import filter.RoleFilter

import cats.data.NonEmptyList
import doobie.*
import doobie.implicits.*
import doobie.util.fragments.{in, whereAnd}

private[persistence] object RoleQuery:

  private val selectRoleFragment = 
    fr"""
      SELECT r.id, r.name, r.system, r.workspace_id
        FROM role r
    """

  private val selectRolePermissionsFragment =
    fr"""
      SELECT p.id, p.name, p.scope
        FROM permission p
    """

  def selectRoleRowById(roleId: Id[Role]): ConnectionIO[Option[RoleRow]] =
    (selectRoleFragment ++ whereAnd(fr"r.id = $roleId"))
      .query[RoleRow]
      .option

  def selectRoleRowsByIds(ids: NonEmptyList[Id[Role]]): ConnectionIO[List[RoleRow]] =
    (selectRoleFragment ++ whereAnd(in(fr"r.id", ids)))
      .query[RoleRow]
      .to[List]

  def selectRoleRowsByFilter(filter: RoleFilter, pagination: Pagination): ConnectionIO[List[RoleRow]] =
    SqlPagination.paginate[RoleRow](selectRoleFragment ++ filter.fragment, pagination)
      .to[List]

  def insertRole(role: Role): ConnectionIO[Id[Role]] =
    sql"""
      INSERT INTO role (name, system, workspace_id)
           VALUES (${role.name}, 
                   ${role.system}, 
                   ${role.workspace})
    """
      .update
      .withUniqueGeneratedKeys[Id[Role]]("id")

  def updateRole(role: Role): ConnectionIO[Int] =
    sql"""
      UPDATE role
         SET name = ${role.name}
       WHERE id = ${role.id}
    """
      .update
      .run

  def deleteRole(roleId: Id[Role]): ConnectionIO[Int] =
    sql"DELETE FROM role r WHERE r.id = $roleId"
      .update
      .run

  def selectRolePermissions(roleId: Id[Role]): ConnectionIO[List[Permission]] =
    (selectRolePermissionsFragment
      ++ whereAnd(fr"p.id IN (SELECT rp.permission_id FROM role_permission rp WHERE rp.role_id = $roleId)"))
      .query[Permission]
      .to[List]

  def selectRolesPermissions(roleIds: NonEmptyList[Id[Role]]): ConnectionIO[List[(Id[Role], Permission)]] =
    (fr"""
      SELECT rp.role_id, p.id, p.name, p.scope
        FROM permission p
        JOIN role_permission rp ON p.id = rp.permission_id
    """ ++ whereAnd(in(fr"rp.role_id", roleIds)))
      .query[(Id[Role], Permission)]
      .to[List]

  def insertRolePermissions(roleId: Id[Role], permissions: NonEmptyList[Permission]): ConnectionIO[Int] =
    val sql  = "INSERT INTO role_permission (role_id, permission_id) VALUES (?, ?)"
    val rows = permissions.map(p => (roleId, p.id))

    Update[RolePermissionRow](sql).updateMany(rows)

  def deleteRolePermissions(roleId: Id[Role]): ConnectionIO[Int] =
    sql"DELETE FROM role_permission rp WHERE rp.role_id = $roleId"
      .update
      .run

end RoleQuery
