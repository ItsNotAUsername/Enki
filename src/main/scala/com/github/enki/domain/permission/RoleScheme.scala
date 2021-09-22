package com.github.enki
package domain
package permission

type RoleScheme       = RoleSchemeF[State.Get]
type CreateRoleScheme = RoleSchemeF[State.Create]

final case class RoleSchemeF[S <: State](
  id:        Field[Id, S],
  name:      Field[String, S],
  default:   Field[Boolean, S],
  workspace: Field[Option[Id], S],
  roles:     Field[List[RoleF[S]], S]
) extends EntityF[RoleSchemeF, S]
