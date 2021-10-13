package com.github.enki
package persistence
package dao
package live

import domain.{Id, Pagination}
import domain.permission.Role
import domain.user.User
import domain.workspace.{Member, Workspace}
import filter.MemberFilter
import mapping.*
import model.MemberRow
import util.ops.list.*

import cats.syntax.all.*
import doobie.ConnectionIO

class LiveMemberDao(roleDao: RoleDao) extends MemberDao:
  import query.MemberQuery

  def insert(member: Member): ConnectionIO[Unit] =
    MemberQuery
      .insert(member.toRow)
      .run
      .void

  def update(member: Member): ConnectionIO[Unit] =
    MemberQuery
      .update(member.toRow)
      .run
      .void
  
  def findByIds(userId: Id[User], workspaceId: Id[Workspace]): ConnectionIO[Option[Member]] =
    for
      memberRow <- MemberQuery.selectById(userId, workspaceId).option
      role      <- memberRow
                     .fold(none.pure[ConnectionIO])(row => roleDao.findById(row.roleId))
    yield
      (memberRow, role).mapN(Member.fromRows)

  def findManyByWorkspaceId(workspaceId: Id[Workspace]): ConnectionIO[List[Member]] =
    MemberQuery.selectByWorkspaceId(workspaceId).to[List] >>= findMembers

  def findManyByUserId(userId: Id[User]): ConnectionIO[List[Member]] =
    MemberQuery.selectByUserId(userId).to[List] >>= findMembers

  def findManyByFilter(filter: MemberFilter, pagination: Pagination): ConnectionIO[List[Member]] =
    MemberQuery.selectByFilter(filter, pagination).to[List] >>= findMembers

  def deleteByIds(userId: Id[User], workspaceId: Id[Workspace]): ConnectionIO[Unit] =
    MemberQuery
      .delete(userId, workspaceId)
      .run
      .void

  def deleteByWorkspaceId(workspaceId: Id[Workspace]): ConnectionIO[Unit] =
    MemberQuery
      .deleteByWorkspaceId(workspaceId)
      .run
      .void

  def deleteByUserId(userId: Id[User]): ConnectionIO[Unit] =
    MemberQuery
      .deleteByUserId(userId)
      .run
      .void

  private def findMembers(memberRows: List[MemberRow]): ConnectionIO[List[Member]] =
    memberRows
      .map(_.roleId)
      .distinct
      .applyOrNil(roleDao.findManyByIds)
      .map { roles =>
        val roleMap = roles.map(r => (r.id, r)).toMap
        
        memberRows.map(row => Member.fromRows(row, roleMap(row.roleId)))
      }

end LiveMemberDao
