package com.github.enki
package persistence
package query

import domain.{Id, Pagination}
import domain.ticket.Ticket
import domain.user.User
import domain.workspace.Workspace
import filter.TicketFilter
import model.TicketRow
import model.meta.given

import doobie.{Query0, Update0}
import doobie.implicits.*
import doobie.postgres.implicits.*
import doobie.util.fragments.whereAnd

object TicketQuery:

  private val selectFragment = fr"""
    SELECT t.id,
           t.workspace_id,
           t.name,
           t.summary,
           t.description,
           t.reporter_id,
           t.assignee_id,
           t.priority,
           t.created,
           t.updated
      FROM ticket t
  """

  def selectById(id: Id[Ticket]): Query0[TicketRow] =
    (selectFragment ++ fr"WHERE t.id = $id")
      .query[TicketRow]

  def selectByWorkspace(id: Id[Workspace], filter: TicketFilter, pagination: Pagination): Query0[TicketRow] =
    SqlPagination
      .paginate[TicketRow](
        selectFragment ++ whereAnd(fr"t.workspace_id = $id", filter.fragment), 
        pagination
      )

  def selectByUser(id: Id[User], filter: TicketFilter, pagination: Pagination): Query0[TicketRow] =
    SqlPagination
      .paginate[TicketRow](
        selectFragment ++ whereAnd(fr"t.assignee_id = $id", filter.fragment),
        pagination
      )

  def insert(ticketRow: TicketRow): Update0 =
    sql"""
      INSERT INTO ticket (workspace_id, name, summary, description, reporter_id, assignee_id, priority, created, updated)
           VALUES (${ticketRow.workspaceId},
                   ${ticketRow.name},
                   ${ticketRow.summary},
                   ${ticketRow.description},
                   ${ticketRow.reporterId},
                   ${ticketRow.assigneeId},
                   ${ticketRow.priority},
                   ${ticketRow.created},
                   ${ticketRow.updated})
    """.update

  def update(ticketRow: TicketRow): Update0 =
    sql"""
      UPDATE ticket
         SET name        = ${ticketRow.name},
             summary     = ${ticketRow.summary},
             description = ${ticketRow.description},
             assignee_id = ${ticketRow.assigneeId},
             priority    = ${ticketRow.priority},
             updated     = ${ticketRow.updated}
       WHERE id = ${ticketRow.id}
    """.update

  def delete(id: Id[Ticket]): Update0 =
    sql"DELETE FROM ticket t WHERE t,id = $id"
      .update

  def deleteByWorkspaceId(id: Id[Workspace]): Update0 =
    sql"DELETE FROM ticket t WHERE t.workspace_id = $id"
      .update

end TicketQuery
