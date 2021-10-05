package com.github.enki
package domain
package workspace

import permission.RoleScheme

import java.time.LocalDateTime

final case class Workspace(
  id:         Id[Workspace],
  name:       WorkspaceName,
  owner:      Member,
  members:    List[Member],
  roleScheme: RoleScheme,
  created:    LocalDateTime,
  updated:    LocalDateTime
)
