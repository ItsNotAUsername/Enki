package com.github.enki
package domain
package permission

import util.hkd.*

type Role       = RoleF[Entity]
type CreateRole = RoleF[Create]
type UpdateRole = RoleF[Update]
type FilterRole = RoleF[Filter]

final case class RoleF[S <: State](
  id:          Field[S, Required, Generated *: Immutable *: End, Id                  ],
  name:        Field[S, Required,                           End, String              ],
  default:     Field[S, Required,              Immutable *: End, Boolean             ],
  permissions: Field[S, Required,                           End, List[PermissionF[S]]]
) extends EntityF[S]
