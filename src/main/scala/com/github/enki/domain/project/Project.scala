package com.github.enki
package domain
package project

import util.hkd.*
import workspace.MemberF

import java.time.LocalDateTime

type Project       = ProjectF[Entity]
type CreateProject = ProjectF[Create]
type UpdateProject = ProjectF[Update]
type FilterProject = ProjectF[Filter]

final case class ProjectF[S <: State](
  id:          Field[S, Required, Generated *: Immutable *: End, Id              ],
  name:        Field[S, Required,                           End, String          ],
  description: Field[S, Required,                           End, String          ],
  lead:        Field[S, Required,                           End, MemberF[S]      ],
  members:     Field[S, Required,                           End, List[MemberF[S]]],
  created:     Field[S, Required,              Immutable *: End, LocalDateTime   ],
  updated:     Field[S, Required,                           End, LocalDateTime   ]
) extends EntityF[S]
