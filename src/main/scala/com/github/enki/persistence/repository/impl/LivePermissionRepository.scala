package com.github.enki
package persistence
package repository
package impl

import domain.Id
import domain.permission.Permission

import cats.effect.MonadCancelThrow
import doobie.*
import doobie.implicits.*

class LivePermissionRepository[F[_]: MonadCancelThrow](xa: Transactor[F]) extends PermissionRepository[F]:
  import query.PermissionQuery

  def findPermissionById(id: Id[Permission]): F[Option[Permission]] =
    PermissionQuery.selectById(id)
      .option
      .transact(xa)

  def findAllPermissions: F[List[Permission]] =
    PermissionQuery.selectAll
      .to[List]
      .transact(xa)

end LivePermissionRepository
