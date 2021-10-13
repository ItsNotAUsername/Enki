package com.github.enki
package domain
package ticket

import workspace.Member

import java.time.LocalDateTime

final case class Ticket(
  id:          Id[Ticket],
  name:        TicketName,
  summary:     TicketSummary,
  description: Option[TicketDescription],
  labels:      List[Label],
  reporter:    Member,
  assignee:    Option[Member],
  priority:    Priority,
  created:     LocalDateTime,
  updated:     LocalDateTime
)
