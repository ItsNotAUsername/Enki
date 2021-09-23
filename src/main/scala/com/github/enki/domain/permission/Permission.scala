package com.github.enki
package domain
package permission

import util.hkd.*

type Permission       = PermissionF[Entity]
type FilterPermission = PermissionF[Filter]

final case class PermissionF[S <: State](
  id:    Field[S, Required, Generated *: Immutable *: End, Id    ],
  name:  Field[S, Required,              Immutable *: End, String],
  scope: Field[S, Required,              Immutable *: End, Scope ]
) extends EntityF[S]
