package com.github.enki
package persistence
package repository

import domain.Id
import domain.permission.*

trait PermissionRepository[F[_]]:
  def findPermissionById(id: Id): F[Option[Permission]]
  def findAllPermissions: F[List[Permission]]
