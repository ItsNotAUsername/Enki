package com.github.enki
package domain
package workspace

import permission.RoleSchemeF
import util.hkd.*

import java.time.LocalDateTime

type Workspace       = WorkspaceF[Entity]
type CreateWorkspace = WorkspaceF[Create]
type UpdateWorkspace = WorkspaceF[Update]
type FilterWorkspace = WorkspaceF[Filter]

final case class WorkspaceF[S <: State](
  id:         Field[S, Required, Generated *: Immutable *: End, Id              ],
  name:       Field[S, Required,                           End, String          ],
  owner:      Field[S, Required,              Immutable *: End, MemberF[S]      ],
  members:    Field[S, Required,                           End, List[MemberF[S]]],
  roleScheme: Field[S, Required,                           End, RoleSchemeF[S]  ],
  created:    Field[S, Required,              Immutable *: End, LocalDateTime   ],
  updated:    Field[S, Required,                           End, LocalDateTime   ]
) extends EntityF[S]
