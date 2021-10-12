package com.github.enki
package persistence
package dao

import domain.Id
import domain.permission.Permission

import cats.data.NonEmptyList as NEL
import doobie.ConnectionIO

trait PermissionDao:
  def findById(id: Id[Permission]): ConnectionIO[Option[Permission]]
  def findManyByIds(ids: NEL[Id[Permission]]): ConnectionIO[List[Permission]]
  def findAll: ConnectionIO[List[Permission]]
