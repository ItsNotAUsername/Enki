package com.github.enki
package persistence
package query

import domain.Id
import domain.permission.Permission
import model.PermissionRow
import model.meta.given

import cats.data.NonEmptyList as NEL
import doobie.Query0
import doobie.implicits.*
import doobie.util.fragments.{in, whereAnd}

private[persistence] object PermissionQuery:

  private val selectFragment = fr"""
    SELECT p.id, p.name, p.scope
      FROM permission p
  """

  def selectById(id: Id[Permission]): Query0[PermissionRow] =
    (selectFragment ++ fr"WHERE p.id = $id")
      .query[PermissionRow]

  def selectByIds(ids: NEL[Id[Permission]]): Query0[PermissionRow] =
    (selectFragment ++ whereAnd(in(fr"p.id", ids)))
      .query[PermissionRow]

  def selectAll: Query0[PermissionRow] =
    selectFragment
      .query[PermissionRow]

end PermissionQuery
