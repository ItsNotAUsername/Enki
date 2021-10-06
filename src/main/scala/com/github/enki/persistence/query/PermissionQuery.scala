package com.github.enki
package persistence
package query

import domain.Id
import domain.permission.Permission
import meta.given

import cats.data.NonEmptyList
import doobie.*
import doobie.implicits.*
import doobie.util.fragments.{in, whereAnd}

private[persistence] object PermissionQuery:

  private val selectFragment = 
    fr"""
      SELECT p.id, p.name, p.scope
        FROM permission p
    """

  def selectPermissionById(id: Id[Permission]): Query0[Permission] =
    (selectFragment ++ whereAnd(fr"p.id = $id"))
      .query[Permission]

  def selectPermissionsByIds(ids: NonEmptyList[Id[Permission]]): Query0[Permission] =
    (selectFragment ++ whereAnd(in(fr"p.id", ids)))
      .query[Permission]

  def selectAll: Query0[Permission] =
    selectFragment.query[Permission]

end PermissionQuery
