package com.github.enki
package persistence
package query

import domain.Id
import domain.ticket.{Label, Ticket}
import model.TicketLabelRow
import model.meta.given

import cats.data.NonEmptyList as NEL
import doobie.{Query0, Update0, Update}
import doobie.implicits.*
import doobie.util.fragments.{in, whereAnd}

object TicketLabelQuery:

  private val selectFragment = fr"""
    SELECT tl.ticket_id, tl.label_id
      FROM ticket_label tl
  """

  def selectByTicketId(id: Id[Ticket]): Query0[TicketLabelRow] =
    (selectFragment ++ fr"WHERE tl.ticket_id = $id")
      .query[TicketLabelRow]

  def selectByTicketIds(ids: NEL[Id[Ticket]]): Query0[TicketLabelRow] =
    (selectFragment ++ whereAnd(in(fr"t.ticket_id", ids)))
      .query[TicketLabelRow]

  def insert: Update[TicketLabelRow] =
    val sql = "INSERT INTO ticket_label (ticket_id, label_id) VALUES (?, ?)"
    Update[TicketLabelRow](sql)

  def deleteByTicketId(id: Id[Ticket]): Update0 =
    sql"DELETE FROM ticket_label tl WHERE tl.ticket_id = $id"
      .update

end TicketLabelQuery
