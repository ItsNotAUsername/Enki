package com.github.enki
package domain
package workspace

import permission.{PermissionF, RoleF}

import java.time.LocalDate

type Member       = MemberF[State.Get]
type CreateMember = MemberF[State.Create]

final case class MemberF[S <: State](
  id:     Field[Id, S],
  name:   Field[String, S],
  email:  Field[String, S],
  role:   Field[RoleF[S], S],
  joined: Field[LocalDate, S]
) extends EntityF[MemberF, S]
