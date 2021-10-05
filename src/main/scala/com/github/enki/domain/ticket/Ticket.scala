package com.github.enki
package domain
package ticket

import field.Priority
import workspace.Member

import java.time.LocalDateTime

final case class Ticket(
  id:          Id[Ticket],
  name:        TicketName,
  summary:     TicketSummary,
  description: Option[TicketDescription],
  labels:      List[String],
  reporter:    Member,
  assignee:    Option[Member],
  priority:    Priority,
  created:     LocalDateTime,
  updated:     LocalDateTime
)
