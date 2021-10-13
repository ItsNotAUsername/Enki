package com.github.enki
package persistence
package dao

import domain.Id
import domain.user.User
import domain.workspace.Workspace

import doobie.ConnectionIO

trait WorkspaceDao:
  def create(workspace: Workspace): ConnectionIO[Id[Workspace]]
  def update(workspace: Workspace): ConnectionIO[Unit]
  def findById(id: Id[Workspace]): ConnectionIO[Option[Workspace]]
  def delete(id: Id[Workspace]): ConnectionIO[Unit]
