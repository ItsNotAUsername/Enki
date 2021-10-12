package com.github.enki
package persistence
package dao
package live

import domain.{Id, Pagination}
import domain.permission.{Permission, Role}
import filter.RoleFilter
import mapping.*
import model.{RolePermissionRow, RoleRow}
import model.meta.given
import util.ops.list.*

import cats.data.NonEmptyList as NEL
import cats.syntax.all.*
import doobie.ConnectionIO
import doobie.implicits.*

class LiveRoleDao(permissionDao: PermissionDao) extends RoleDao:
  import query.{RolePermissionQuery, RoleQuery}

  def create(role: Role): ConnectionIO[Id[Role]] =
    for 
      roleId <- RoleQuery
                  .insert(role.toRow)
                  .withUniqueGeneratedKeys[Id[Role]]("id")
      _      <- RolePermissionQuery
                  .insert
                  .updateMany(
                    role.permissions.map(p => RolePermissionRow(roleId, p.id))
                  )
    yield roleId

  def update(role: Role): ConnectionIO[Unit] = 
    RoleQuery
      .update(role.toRow)
      .run
      .void

  def findById(id: Id[Role]): ConnectionIO[Option[Role]] =
    for
      roleRow            <- RoleQuery.selectById(id).option
      rolePermissionRows <- RolePermissionQuery.selectByRoleId(id).to[List]
      permissions        <- findPermissions(rolePermissionRows.map(_.permissionId))
    yield 
      (roleRow, permissions.toNel).mapN(Role.fromRows)

  def findManyByIds(ids: NEL[Id[Role]]): ConnectionIO[List[Role]] = 
    for 
      roleRows           <- RoleQuery.selectByIds(ids).to[List]
      rolePermissionRows <- RolePermissionQuery.selectByRoleIds(ids).to[List]
      permissions        <- findPermissions(rolePermissionRows.map(_.permissionId).distinct)
    yield
      createRoles(roleRows, rolePermissionRows, permissions)

  def findManyByFilter(filter: RoleFilter, pagination: Pagination): ConnectionIO[List[Role]] =
    for
      roleRows           <- RoleQuery.selectByFilter(filter, pagination).to[List]
      rolePermissionRows <- roleRows
                              .map(_.id)
                              .applyOrNil(RolePermissionQuery.selectByRoleIds(_).to[List])
      permissions        <- findPermissions(rolePermissionRows.map(_.permissionId).distinct)
    yield
      createRoles(roleRows, rolePermissionRows, permissions)

  def delete(id: Id[Role]): ConnectionIO[Unit] =
    for
      _ <- RolePermissionQuery.deleteByRoleId(id).run
      _ <- RoleQuery.delete(id).run
    yield ()

  private def findPermissions(ids: List[Id[Permission]]): ConnectionIO[List[Permission]] =
    ids.applyOrNil(permissionDao.findManyByIds)

  private def createRoles(
    roleRows:           List[RoleRow], 
    rolePermissionRows: List[RolePermissionRow],
    permissions:        List[Permission]
  ): List[Role] =
    val rolePermissionMap = rolePermissionRows.groupMapNel(_.roleId)(_.permissionId)
    val permissionMap     = permissions.map(p => (p.id, p)).toMap

    roleRows.map { row => 
      Role.fromRows(
        row, 
        rolePermissionMap(row.id).map(permissionMap.apply)
      )
    }

end LiveRoleDao
