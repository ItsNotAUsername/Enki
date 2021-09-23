package com.github.enki
package domain
package permission

import util.hkd.*

type RoleScheme       = RoleSchemeF[Entity]
type CreateRoleScheme = RoleSchemeF[Create]
type UpdateRoleScheme = RoleSchemeF[Update]
type FilterRoleScheme = RoleSchemeF[Filter]

final case class RoleSchemeF[S <: State](
  id:        Field[S, Required, Generated *: Immutable *: End, Id            ],
  name:      Field[S, Required,                           End, String        ],
  default:   Field[S, Required,              Immutable *: End, Boolean       ],
  workspace: Field[S, Optional,              Immutable *: End, Id            ],
  roles:     Field[S, Required,                           End, List[RoleF[S]]]
) extends EntityF[S]
