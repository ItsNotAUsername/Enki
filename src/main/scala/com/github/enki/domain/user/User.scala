package com.github.enki
package domain
package user

import java.time.LocalDateTime
import java.util.UUID

final case class User(
  id:       Id[User],
  username: Username,
  email:    Email,
  password: HashedPassword,
  active:   Boolean,
  code:     UUID,
  created:  LocalDateTime,
  updated:  LocalDateTime
)
