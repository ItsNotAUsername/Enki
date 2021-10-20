package com.github.enki
package persistence
package query

import domain.{Id, Pagination}
import domain.user.User
import domain.workspace.{Member, Workspace}
import filter.MemberFilter
import model.MemberRow

import cats.data.NonEmptyList as NEL
import doobie.{Query0, Update0}
import doobie.implicits.*
import doobie.postgres.implicits.*
import doobie.util.fragments.{in, whereAnd}

private[persistence] object MemberQuery:

  private val selectFragment = fr"""
    SELECT wm.user_id,
           wm.workspace_id,
           wm.role_id
           u.username,
           u.email,
           wm.status,
           wm.joined,
           wm.updated
      FROM workspace_member wm
      JOIN usr u ON wm.user_id = u.id
  """

  private val deleteFragment = fr"DELETE FROM workspace_member wm"

  private def userFragment(userId: Id[User]) = fr"wm.user_id = $userId"

  private def workspaceFragment(workspaceId: Id[Workspace]) = fr"wm.workspace_id = $workspaceId"

  def selectById(userId: Id[User], workspaceId: Id[Workspace]): Query0[MemberRow] =
    (selectFragment ++ whereAnd(userFragment(userId), workspaceFragment(workspaceId)))
      .query[MemberRow]

  def selectByIds(ids: NEL[(Id[User], Id[Workspace])]): Query0[MemberRow] =
    (selectFragment ++ whereAnd(in(fr"(wm.user_id, wm.workspace_id)", ids)))
      .query[MemberRow]

  def selectByWorkspaceId(workspaceId: Id[Workspace]): Query0[MemberRow] =
    (selectFragment ++ whereAnd(workspaceFragment(workspaceId)))
      .query[MemberRow]

  def selectByUserId(userId: Id[User]): Query0[MemberRow] =
    (selectFragment ++ whereAnd(userFragment(userId)))
      .query[MemberRow]

  def selectByFilter(filter: MemberFilter, pagination: Pagination): Query0[MemberRow] =
    SqlPagination
      .paginate[MemberRow](selectFragment ++ filter.fragment, pagination)

  def insert(memberRow: MemberRow): Update0 =
    sql"""
      INSERT INTO workspace_member (user_id, workspace_id, role_id, status, joined, updated)
           VALUES (${memberRow.userId},
                   ${memberRow.workspaceId},
                   ${memberRow.roleId},
                   ${memberRow.status},
                   ${memberRow.joined},
                   ${memberRow.updated})
    """.update

  def update(memberRow: MemberRow): Update0 =
    (
      fr"""
        UPDATE workspace_member
          SET role_id = ${memberRow.roleId},
              status  = ${memberRow.status},
              updated = ${memberRow.updated}
      """ ++ whereAnd(userFragment(memberRow.userId), workspaceFragment(memberRow.workspaceId))
    ).update

  def delete(userId: Id[User], workspaceId: Id[Workspace]): Update0 =
    (deleteFragment ++ whereAnd(userFragment(userId), workspaceFragment(workspaceId)))
      .update

  def deleteByWorkspaceId(workspaceId: Id[Workspace]): Update0 =
    (deleteFragment ++ whereAnd(workspaceFragment(workspaceId)))
      .update

  def deleteByUserId(userId: Id[User]): Update0 = 
    (deleteFragment ++ whereAnd(userFragment(userId)))
      .update

end MemberQuery
