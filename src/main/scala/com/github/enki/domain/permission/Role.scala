package com.github.enki
package domain
package permission

type Role       = RoleF[State.Get]
type CreateRole = RoleF[State.Create]

final case class RoleF[S <: State](
  id:          Field[Id, S],
  name:        Field[String, S],
  default:     Field[Boolean, S],
  permissions: Field[List[PermissionF[S]], S]
) extends EntityF[RoleF, S]
