package com.github.enki
package persistence
package dao
package live

import domain.Id
import domain.permission.Permission
import mapping.*
import model.meta.given

import cats.data.NonEmptyList as NEL
import doobie.*
import doobie.implicits.*
import doobie.util.fragments.in

class LivePermissionDao extends PermissionDao:
  import query.PermissionQuery

  def findById(id: Id[Permission]): ConnectionIO[Option[Permission]] =
    PermissionQuery
      .selectById(id)
      .option
      .map(_.map(Permission.fromRow))

  def findManyByIds(ids: NEL[Id[Permission]]): ConnectionIO[List[Permission]] =
    PermissionQuery
      .selectByIds(ids)
      .to[List]
      .map(_.map(Permission.fromRow))
  
  def findAll: ConnectionIO[List[Permission]] =
    PermissionQuery
      .selectAll
      .to[List]
      .map(_.map(Permission.fromRow))

end LivePermissionDao
