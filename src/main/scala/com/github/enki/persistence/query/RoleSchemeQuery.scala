package com.github.enki
package persistence
package query

import domain.{Id, Pagination}
import domain.permission.RoleScheme
import filter.RoleSchemeFilter
import model.RoleSchemeRow
import model.meta.given

import cats.data.NonEmptyList as NEL
import doobie.{Query0, Update0}
import doobie.implicits.*
import doobie.util.fragments.{in, whereAnd}

private[persistence] object RoleSchemeQuery:

  private val selectFragment = fr"""
    SELECT rs.id, rs.name, rs.system, rs.workspace_id
      FROM role_scheme rs
  """

  def selectById(id: Id[RoleScheme]): Query0[RoleSchemeRow] =
    (selectFragment ++ fr"WHERE rs.id = $id")
      .query[RoleSchemeRow]

  def selectByIds(ids: NEL[Id[RoleScheme]]): Query0[RoleSchemeRow] =
    (selectFragment ++ whereAnd(in(fr"rs.id", ids)))
      .query[RoleSchemeRow]

  def selectByFilter(filter: RoleSchemeFilter, pagination: Pagination): Query0[RoleSchemeRow] =
    SqlPagination
      .paginate[RoleSchemeRow](selectFragment ++ whereAnd(filter.fragment), pagination)

  def insert(roleScheme: RoleSchemeRow): Update0 =
    sql"""
      INSERT INTO role_scheme (name, system, workspace_id)
           VALUES (${roleScheme.name},
                   ${roleScheme.system},
                   ${roleScheme.workspace})
    """.update

  def update(roleScheme: RoleSchemeRow): Update0 =
    sql"""
      UPDATE role_scheme
         SET name = ${roleScheme.name}
       WHERE id = ${roleScheme.id}
    """.update

  def delete(id: Id[RoleScheme]): Update0 =
    sql"DELETE FROM role_scheme rs WHERE rs.id = $id"
      .update

end RoleSchemeQuery
