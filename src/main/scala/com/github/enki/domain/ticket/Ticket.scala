package com.github.enki
package domain
package ticket

import field.Priority
import util.hkd.*
import workspace.MemberF

import java.time.LocalDateTime

type Ticket       = TicketF[Entity]
type CreateTicket = TicketF[Create]
type UpdateTicket = TicketF[Update]
type FilterTicket = TicketF[Filter]

final case class TicketF[S <: State](
  id:          Field[S, Required, Generated *: Immutable *: End, Id           ],
  name:        Field[S, Required,                           End, String       ],
  summary:     Field[S, Required,                           End, String       ],
  description: Field[S, Optional,                           End, String       ],
  labels:      Field[S, Required,                           End, List[String] ],
  reporter:    Field[S, Required,              Immutable *: End, MemberF[S]   ],
  assignee:    Field[S, Optional,                           End, MemberF[S]   ],
  priority:    Field[S, Required,                           End, Priority     ],
  created:     Field[S, Required,              Immutable *: End, LocalDateTime],
  updated:     Field[S, Required,                           End, LocalDateTime]
) extends EntityF[S]
