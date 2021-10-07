package com.github.enki
package persistence
package repository

import domain.{Id, Pagination}
import domain.permission.Role
import filter.RoleFilter

import cats.data.NonEmptyList

trait RoleRepository[F[_]]:
  def createRole(role: Role): F[Role]
  def updateRole(role: Role): F[Role]
  def findRoleById(roleId: Id[Role]): F[Option[Role]]
  def findRolesByIds(roleIds: NonEmptyList[Id[Role]]): F[List[Role]]
  def findRolesByFilter(filter: RoleFilter, pagination: Pagination): F[List[Role]]
  def deleteRole(roleId: Id[Role]): F[Unit]
