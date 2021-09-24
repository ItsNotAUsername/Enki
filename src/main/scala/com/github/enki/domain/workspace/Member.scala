package com.github.enki
package domain
package workspace

import permission.{PermissionF, RoleF}
import util.hkd.*

import java.time.LocalDateTime

type Member       = MemberF[Entity]
type CreateMember = MemberF[Create]
type UpdateMember = MemberF[Update]
type FilterMember = MemberF[Filter]

final case class MemberF[S <: State](
  id:          Field[S, Required, Immutable *: End, Id           ],
  workspaceId: Field[S, Required, Immutable *: End, Id           ],
  name:        Field[S, Required, Immutable *: End, String       ],
  email:       Field[S, Required, Immutable *: End, String       ],
  role:        Field[S, Required,              End, RoleF[S]     ],
  status:      Field[S, Required,              End, MemberStatus ],
  joined:      Field[S, Required, Immutable *: End, LocalDateTime]
) extends EntityF[S]
