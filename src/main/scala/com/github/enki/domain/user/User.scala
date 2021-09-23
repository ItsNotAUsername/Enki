package com.github.enki
package domain
package user

import util.hkd.*

import java.time.LocalDateTime
import java.util.UUID

type User       = UserF[Entity]
type CreateUser = UserF[Create]
type UpdateUser = UserF[Update]
type FilterUser = UserF[Filter]

final case class UserF[S <: State](
  id:       Field[S, Required, Generated *: Immutable *: End, Id           ],
  username: Field[S, Required,                           End, String       ],
  email:    Field[S, Required,                           End, String       ],
  password: Field[S, Required,                           End, String       ],
  active:   Field[S, Required,                           End, String       ],
  code:     Field[S, Required,              Immutable *: End, UUID         ],
  created:  Field[S, Required,              Immutable *: End, LocalDateTime],
  updated:  Field[S, Required,                           End, LocalDateTime]
) extends EntityF[S]
