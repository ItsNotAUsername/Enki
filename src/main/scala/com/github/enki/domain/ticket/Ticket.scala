package com.github.enki
package domain
package ticket

import field.TicketFieldF
import workspace.MemberF

type Ticket       = TicketF[State.Get]
type CreateTicket = TicketF[State.Create]

final case class TicketF[S <: State](
  id:     Field[Id, S],
  fields: Field[List[TicketFieldF[S]], S]
) extends EntityF[TicketF, S]
