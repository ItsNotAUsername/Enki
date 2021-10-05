package com.github.enki
package persistence
package repository

import domain.Id
import domain.permission.Permission

trait PermissionRepository[F[_]]:
  def findPermissionById(id: Id[Permission]): F[Option[Permission]]
  def findAllPermissions: F[List[Permission]]
