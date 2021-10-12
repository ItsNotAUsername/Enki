package com.github.enki
package persistence
package dao

import domain.{Id, Pagination}
import domain.permission.RoleScheme
import filter.RoleSchemeFilter

import cats.data.NonEmptyList as NEL
import doobie.ConnectionIO

trait RoleSchemeDao:
  def create(roleScheme: RoleScheme): ConnectionIO[Id[RoleScheme]]
  def update(roleScheme: RoleScheme): ConnectionIO[Unit]
  def findById(id: Id[RoleScheme]): ConnectionIO[Option[RoleScheme]]
  def findManyByIds(ids: NEL[Id[RoleScheme]]): ConnectionIO[List[RoleScheme]]
  def findManyByFilter(filter: RoleSchemeFilter, pagination: Pagination): ConnectionIO[List[RoleScheme]]
  def delete(id: Id[RoleScheme]): ConnectionIO[Unit]
