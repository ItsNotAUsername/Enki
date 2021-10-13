package com.github.enki
package persistence
package dao

import domain.{Id, Pagination}
import domain.user.User
import domain.workspace.{Member, Workspace}
import filter.MemberFilter

import cats.data.NonEmptyList as NEL
import doobie.ConnectionIO

trait MemberDao:
  def insert(member: Member): ConnectionIO[Unit]
  def update(member: Member): ConnectionIO[Unit]
  def findByIds(userId: Id[User], workspaceId: Id[Workspace]): ConnectionIO[Option[Member]]
  def findManyByIds(ids: NEL[(Id[User], Id[Workspace])]): ConnectionIO[List[Member]]
  def findManyByWorkspaceId(workspaceId: Id[Workspace]): ConnectionIO[List[Member]]
  def findManyByUserId(userId: Id[User]): ConnectionIO[List[Member]]
  def findManyByFilter(filter: MemberFilter, pagination: Pagination): ConnectionIO[List[Member]]
  def deleteByIds(userId: Id[User], workspaceId: Id[Workspace]): ConnectionIO[Unit]
  def deleteByWorkspaceId(workspaceId: Id[Workspace]): ConnectionIO[Unit]
  def deleteByUserId(userId: Id[User]): ConnectionIO[Unit]
