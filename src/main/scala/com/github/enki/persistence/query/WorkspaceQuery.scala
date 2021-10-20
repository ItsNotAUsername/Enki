package com.github.enki
package persistence
package query

import domain.{Id, Pagination}
import domain.user.User
import domain.workspace.Workspace
import model.WorkspaceRow

import cats.data.NonEmptyList as NEL
import doobie.{Query0, Update0}
import doobie.implicits.*
import doobie.postgres.implicits.*

private[persistence] object WorkspaceQuery:

  private val selectFragment = fr"""
    SELECT w.id,
           w.name,
           w.owner_id,
           w.role_scheme_id,
           w.created,
           w.updated
      FROM workspace w
  """

  private val selectJoinFragment = fr"""
    SELECT w.id,
           w.name,
           w.owner_id,
           w.role_scheme_id,
           w.created,
           w.updated
      FROM workspace_member wm
      JOIN workspace w ON wm.workspace_id = w.id
  """

  def selectById(workspaceId: Id[Workspace]): Query0[WorkspaceRow] =
    (selectFragment ++ fr"WHERE w.id = $workspaceId")
      .query[WorkspaceRow]

  def selectByUserId(userId: Id[User], pagination: Pagination): Query0[WorkspaceRow] =
    SqlPagination
      .paginate[WorkspaceRow](selectJoinFragment ++ fr"WHERE wm.user_id = $userId", pagination)

  def insert(workspaceRow: WorkspaceRow): Update0 =
    sql"""
      INSERT INTO workspace (id, name, owner_id, role_scheme_id, created, updated)
           VALUES (${workspaceRow.id},
                   ${workspaceRow.name},
                   ${workspaceRow.ownerId},
                   ${workspaceRow.roleSchemeId},
                   ${workspaceRow.created},
                   ${workspaceRow.updated})
    """.update

  def update(workspaceRow: WorkspaceRow): Update0 =
    sql"""
      UPDATE workspace
         SET name           = ${workspaceRow.name},
             owner_id       = ${workspaceRow.ownerId},
             role_scheme_id = ${workspaceRow.roleSchemeId},
             updated        = ${workspaceRow.updated}
       WHERE id = ${workspaceRow.id}
    """.update

  def delete(id: Id[Workspace]): Update0 =
    sql"DELETE FROM workspace w WHERE w.id = $id"
      .update

end WorkspaceQuery
