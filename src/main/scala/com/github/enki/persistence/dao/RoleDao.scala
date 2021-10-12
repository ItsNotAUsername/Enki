package com.github.enki
package persistence
package dao

import domain.{Id, Pagination}
import domain.permission.{Permission, Role}
import filter.RoleFilter

import cats.data.NonEmptyList as NEL
import doobie.ConnectionIO

trait RoleDao:
  def create(role: Role): ConnectionIO[Id[Role]]
  def update(role: Role): ConnectionIO[Unit]
  def findById(id: Id[Role]): ConnectionIO[Option[Role]]
  def findManyByIds(ids: NEL[Id[Role]]): ConnectionIO[List[Role]]
  def findManyByFilter(filter: RoleFilter, pagination: Pagination): ConnectionIO[List[Role]]
  def delete(id: Id[Role]): ConnectionIO[Unit]
