package com.github.enki
package domain
package permission

type Permission       = PermissionF[State.Get]
type CreatePermission = PermissionF[State.Create]

final case class PermissionF[S <: State](
  id:    Field[Id, S],
  name:  Field[String, S],
  scope: Field[Scope, S]
) extends EntityF[PermissionF, S]
