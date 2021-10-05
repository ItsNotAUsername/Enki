package com.github.enki
package domain
package workspace

import permission.{Permission, Role}
import user.User

import java.time.LocalDateTime

final case class Member(
  userId:      Id[User],
  workspaceId: Id[Workspace],
  name:        Username,
  email:       Email,
  role:        Role,
  status:      MemberStatus,
  joined:      LocalDateTime,
  updated:     LocalDateTime
)
