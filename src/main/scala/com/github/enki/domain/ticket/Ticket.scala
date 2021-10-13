package com.github.enki
package domain
package ticket

import workspace.{Member, Workspace}

import java.time.LocalDateTime

final case class Ticket(
  id:          Id[Ticket],
  workspaceId: Id[Workspace],
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
