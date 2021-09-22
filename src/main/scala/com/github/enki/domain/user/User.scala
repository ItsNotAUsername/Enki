package com.github.enki
package domain
package user

import java.time.LocalDateTime
import java.util.UUID

type User       = UserF[State.Get]
type CreateUser = UserF[State.Create]

final case class UserF[S <: State](
  id:       Field[Id, S],
  username: Field[String, S],
  email:    Field[String, S],
  password: Field[String, S],
  active:   Field[Boolean, S],
  code:     Field[UUID, S],
  created:  Field[LocalDateTime, S],
  updated:  Field[LocalDateTime, S]
) extends EntityF[UserF, S]
