package com.github.enki
package persistence
package dao
package live

import domain.{Id, Pagination}
import domain.ticket.{Label, Ticket}
import domain.user.User
import domain.workspace.{Member, Workspace}
import filter.TicketFilter
import mapping.*
import model.{TicketLabelRow, TicketRow}
import util.ops.list.*
import util.ops.option.*

import cats.data.NonEmptyList as NEL
import cats.syntax.all.*
import doobie.ConnectionIO
import com.github.enki.persistence.query.TicketLabelQuery

class LiveTicketDao(labelDao: LabelDao, memberDao: MemberDao) extends TicketDao:
  import query.{TicketLabelQuery, TicketQuery}

  def create(ticket: Ticket): ConnectionIO[Id[Ticket]] = 
    for
      ticketId <- TicketQuery
                    .insert(ticket.toRow)
                    .withUniqueGeneratedKeys[Id[Ticket]]("id")
      _        <- TicketLabelQuery
                    .insert
                    .updateMany(ticket.labels.map(l => TicketLabelRow(ticketId, l.id)))
    yield ticketId

  def update(ticket: Ticket): ConnectionIO[Unit] = 
    TicketQuery
      .update(ticket.toRow)
      .run
      .void

  def findById(id: Id[Ticket]): ConnectionIO[Option[Ticket]] =
    for
      ticketRow       <- TicketQuery.selectById(id).option
      ticketLabelRows <- TicketLabelQuery.selectByTicketId(id).to[List]
      labels          <- findLabels(ticketLabelRows.map(_.labelId))
      members         <- findMembers(ticketRow.map(membersFromRow).toList.flatten)
    yield
      (
        ticketRow, 
        labels.some, 
        ticketRow.flatMap(row => members.find(_.userId == row.reporterId)), 
        ticketRow.flatMap(_.assigneeId.map(id => members.find(_.userId == id)))
      ).mapN(Ticket.fromRows)

  def findManyByWorkspaceId(id: Id[Workspace], filter: TicketFilter, pagination: Pagination): ConnectionIO[List[Ticket]] =
    TicketQuery.selectByWorkspace(id, filter, pagination).to[List] >>= findTickets

  def findManyByUserId(id: Id[User], filter: TicketFilter, pagination: Pagination): ConnectionIO[List[Ticket]] = 
    TicketQuery.selectByUser(id, filter, pagination).to[List] >>= findTickets

  def delete(id: Id[Ticket]): ConnectionIO[Unit] =
    for
      _ <- TicketLabelQuery.deleteByTicketId(id).run
      _ <- TicketQuery.delete(id).run
    yield ()

  private def findLabels(labels: List[Id[Label]]): ConnectionIO[List[Label]] =
    labels.applyOrNil(labelDao.findByIds)

  private def membersFromRow(ticketRow: TicketRow): List[(Id[User], Id[Workspace])] = 
    (ticketRow.reporterId, ticketRow.workspaceId) 
      :: ticketRow.assigneeId.map((_, ticketRow.workspaceId)).toList

  private def findMembers(ids: List[(Id[User], Id[Workspace])]): ConnectionIO[List[Member]] =
    ids.applyOrNil(memberDao.findManyByIds)

  private def findTickets(ticketRows: List[TicketRow]): ConnectionIO[List[Ticket]] =
    for
      ticketLabelRows <- ticketRows.map(_.id).applyOrNil(TicketLabelQuery.selectByTicketIds(_).to[List])
      labels          <- findLabels(ticketLabelRows.map(_.labelId).distinct)
      members         <- findMembers(ticketRows.flatMap(membersFromRow).distinct)
    yield
      val ticketLabelMap = ticketLabelRows.groupMap(_.ticketId)(_.labelId)
      val labelMap       = labels.map(l => (l.id, l)).toMap
      val memberMap      = members.map(m => (m.userId, m)).toMap

      ticketRows.map { row =>
        Ticket.fromRows(
          row,
          ticketLabelMap(row.id).map(labelMap.apply),
          memberMap(row.reporterId),
          row.assigneeId.map(memberMap.apply)
        )
      }

end LiveTicketDao
