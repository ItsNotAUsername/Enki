package com.github.enki
package persistence
package dao
package live

import domain.{Id, Pagination}
import domain.user.User
import domain.workspace.Workspace
import mapping.*
import model.WorkspaceRow
import model.meta.given
import util.ops.option.*

import cats.syntax.all.*
import doobie.ConnectionIO

class LiveWorkspaceDao(memberDao: MemberDao, roleSchemeDao: RoleSchemeDao) extends WorkspaceDao:
  import query.WorkspaceQuery

  def create(workspace: Workspace): ConnectionIO[Id[Workspace]] =
    WorkspaceQuery
      .insert(workspace.toRow)
      .withUniqueGeneratedKeys[Id[Workspace]]("id")

  def update(workspace: Workspace): ConnectionIO[Unit] =
    WorkspaceQuery
      .update(workspace.toRow)
      .run
      .void

  def findById(id: Id[Workspace]): ConnectionIO[Option[Workspace]] =
    for
      workspaceRow <- WorkspaceQuery.selectById(id).option
      owner        <- workspaceRow.applyOrNone(row => memberDao.findByIds(row.ownerId, id))
      members      <- memberDao.findManyByWorkspaceId(id)
      roleScheme   <- workspaceRow.applyOrNone(row => roleSchemeDao.findById(row.roleSchemeId))
    yield
      (workspaceRow, owner, members.toNel, roleScheme).mapN(Workspace.fromRows)

  def delete(id: Id[Workspace]): ConnectionIO[Unit] =
    WorkspaceQuery
      .delete(id)
      .run
      .void

end LiveWorkspaceDao
