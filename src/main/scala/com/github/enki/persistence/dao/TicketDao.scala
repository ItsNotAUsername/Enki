package com.github.enki
package persistence
package dao

import domain.{Id, Pagination}
import domain.ticket.Ticket
import domain.user.User
import domain.workspace.Workspace
import filter.TicketFilter

import cats.data.NonEmptyList as NEL
import doobie.ConnectionIO

trait TicketDao:
  def create(ticket: Ticket): ConnectionIO[Id[Ticket]]
  def update(ticket: Ticket): ConnectionIO[Unit]
  def findById(id: Id[Ticket]): ConnectionIO[Option[Ticket]]
  def findManyByWorkspaceId(id: Id[Workspace], filter: TicketFilter, pagination: Pagination): ConnectionIO[List[Ticket]]
  def findManyByUserId(id: Id[User], filter: TicketFilter, pagination: Pagination): ConnectionIO[List[Ticket]]
  def delete(id: Id[Ticket]): ConnectionIO[Unit]
