package com.github.enki
package persistence
package dao
package live

import domain.{Id, Pagination}
import domain.permission.{RoleScheme, Role}
import filter.RoleSchemeFilter
import mapping.*
import model.{RoleSchemeRoleRow, RoleSchemeRow}
import model.meta.given
import util.ops.list.*

import cats.data.NonEmptyList as NEL
import cats.syntax.all.*
import doobie.ConnectionIO
import doobie.implicits.*

class LiveRoleSchemeDao(roleDao: RoleDao) extends RoleSchemeDao:
  import query.{RoleSchemeRoleQuery, RoleSchemeQuery}

  def create(roleScheme: RoleScheme): ConnectionIO[Id[RoleScheme]] =
    for
      roleSchemeId <- RoleSchemeQuery
                        .insert(roleScheme.toRow)
                        .withUniqueGeneratedKeys[Id[RoleScheme]]("id")
      _            <- RoleSchemeRoleQuery
                        .insert
                        .updateMany(roleScheme.roles.map(r => RoleSchemeRoleRow(roleSchemeId, r.id)))
    yield roleSchemeId
  
  def update(roleScheme: RoleScheme): ConnectionIO[Unit] =
    RoleSchemeQuery
      .update(roleScheme.toRow)
      .run
      .void

  def findById(id: Id[RoleScheme]): ConnectionIO[Option[RoleScheme]] =
    for
      roleSchemeRow      <- RoleSchemeQuery.selectById(id).option
      roleSchemeRoleRows <- RoleSchemeRoleQuery.selectByRoleSchemeId(id).to[List]
      roles              <- findRoles(roleSchemeRoleRows.map(_.roleId))
    yield 
      (roleSchemeRow, roles.toNel).mapN(RoleScheme.fromRows)
  
  def findManyByIds(ids: NEL[Id[RoleScheme]]): ConnectionIO[List[RoleScheme]] =
    for
      roleSchemeRows     <- RoleSchemeQuery.selectByIds(ids).to[List]
      roleSchemeRoleRows <- RoleSchemeRoleQuery.selectByRoleSchemeIds(ids).to[List]
      roles              <- findRoles(roleSchemeRoleRows.map(_.roleId).distinct)
    yield
      createRoleSchemes(roleSchemeRows, roleSchemeRoleRows, roles)
  
  def findManyByFilter(filter: RoleSchemeFilter, pagination: Pagination): ConnectionIO[List[RoleScheme]] =
    for
      roleSchemeRows     <- RoleSchemeQuery.selectByFilter(filter, pagination).to[List]
      roleSchemeRoleRows <- roleSchemeRows
                              .map(_.id)
                              .applyOrNil(RoleSchemeRoleQuery.selectByRoleSchemeIds(_).to[List])
      roles              <- findRoles(roleSchemeRoleRows.map(_.roleId).distinct)
    yield
      createRoleSchemes(roleSchemeRows, roleSchemeRoleRows, roles)
  
  def delete(id: Id[RoleScheme]): ConnectionIO[Unit] =
    for
      _ <- RoleSchemeRoleQuery.deleteByRoleSchemeId(id).run
      _ <- RoleSchemeQuery.delete(id).run
    yield ()

  private def findRoles(ids: List[Id[Role]]): ConnectionIO[List[Role]] =
    ids.applyOrNil(roleDao.findManyByIds)

  private def createRoleSchemes(
    roleSchemeRows:     List[RoleSchemeRow],
    roleSchemeRoleRows: List[RoleSchemeRoleRow],
    roles:              List[Role]
  ): List[RoleScheme] =
    val roleSchemeRoleMap = roleSchemeRoleRows.groupMapNel(_.roleSchemeId)(_.roleId)
    val roleMap           = roles.map(r => (r.id, r)).toMap

    roleSchemeRows.map { row =>
      RoleScheme.fromRows(
        row,
        roleSchemeRoleMap(row.id).map(roleMap.apply)
      )
    } 

end LiveRoleSchemeDao
