package com.github.enki
package persistence
package repository

import domain.Id
import domain.permission.Permission

import cats.data.NonEmptyList

trait PermissionRepository[F[_]]:
  def findPermissionById(id: Id[Permission]): F[Option[Permission]]
  def findPermissionsByIds(ids: NonEmptyList[Id[Permission]]): F[List[Permission]]
  def findAllPermissions: F[List[Permission]]
