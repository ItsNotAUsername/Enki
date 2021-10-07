package com.github.enki
package persistence
package repository
package impl

import domain.{Id, Pagination}
import domain.permission.{Permission, Role}
import filter.RoleFilter

import cats.data.NonEmptyList
import cats.effect.MonadCancelThrow
import cats.syntax.all.*
import doobie.*
import doobie.implicits.*

class LiveRoleRepository[F[_]: MonadCancelThrow](xa: Transactor[F]) extends RoleRepository[F]:
  import query.{RoleRow, RoleQuery}

  def createRole(role: Role): F[Role] =
    (
      for
        roleId <- RoleQuery.insertRole(role)
        _      <- RoleQuery.insertRolePermissions(roleId, role.permissions)
      yield role.copy(id = roleId)
    ).transact(xa)

  def updateRole(role: Role): F[Role] =
    (
      for
        _ <- RoleQuery.updateRole(role)
        _ <- RoleQuery.deleteRolePermissions(role.id)
        _ <- RoleQuery.insertRolePermissions(role.id, role.permissions)
      yield ()
    ).transact(xa)
      .as(role)

  def findRoleById(roleId: Id[Role]): F[Option[Role]] =
    (
      for
        mbRole <- RoleQuery.selectRoleRowById(roleId)
        perms  <- RoleQuery.selectRolePermissions(roleId).map(_.toNel)
      yield (mbRole, perms).mapN(role)
    ).transact(xa)

  def findRolesByIds(roleIds: NonEmptyList[Id[Role]]): F[List[Role]] =
    (
      for
        roleRows <- RoleQuery.selectRoleRowsByIds(roleIds)
        perms    <- RoleQuery.selectRolesPermissions(roleIds).map(groupPermissionsByRoleId)
      yield roleRows.map(row => role(row, perms(row._1)))
    ).transact(xa)

  def findRolesByFilter(filter: RoleFilter, pagination: Pagination = Pagination.default): F[List[Role]] =
    (
      for
        roleRows <- RoleQuery.selectRoleRowsByFilter(filter, pagination)
        perms    <- roleRows.map(_._1)
                      .toNel
                      .fold(Map.empty.pure[ConnectionIO]) { ids =>
                        RoleQuery.selectRolesPermissions(ids).map(groupPermissionsByRoleId)
                      }
      yield roleRows.map(row => role(row, perms(row._1)))
    ).transact(xa)

  def deleteRole(roleId: Id[Role]): F[Unit] =
    (
      for
        _ <- RoleQuery.deleteRolePermissions(roleId)
        _ <- RoleQuery.deleteRole(roleId)
      yield ()
    ).transact(xa)

  private def role(roleRow: RoleRow, permissions: NonEmptyList[Permission]): Role =
    Role(roleRow._1, roleRow._2, roleRow._3, roleRow._4, permissions)

  private def groupPermissionsByRoleId(perms: List[(Id[Role], Permission)]): Map[Id[Role], NonEmptyList[Permission]] =
    perms.groupMap(_._1)(_._2)
      .collect { case (roleId, p :: ps) => (roleId, NonEmptyList(p, ps)) }

end LiveRoleRepository
