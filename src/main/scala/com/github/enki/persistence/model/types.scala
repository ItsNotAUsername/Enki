package com.github.enki
package persistence
package model

import domain.*
import domain.permission.*
import domain.ticket.*
import domain.user.*
import domain.workspace.*

import java.time.LocalDateTime
import java.util.UUID

final case class UserRow(
  id:       Id[User],
  username: Username,
  email:    Email,
  password: HashedPassword,
  active:   Boolean,
  code:     UUID,
  created:  LocalDateTime,
  updated:  LocalDateTime
)

final case class PermissionRow(
  id:    Id[Permission],
  name:  PermissionName,
  scope: Scope
)

final case class RoleRow(
  id:        Id[Role],
  name:      RoleName,
  system:    Boolean,
  workspace: Option[Id[Workspace]]
)

final case class RolePermissionRow(
  roleId:       Id[Role],
  permissionId: Id[Permission]
)

final case class RoleSchemeRow(
  id:        Id[RoleScheme],
  name:      RoleSchemeName,
  system:    Boolean,
  workspace: Option[Id[Workspace]]
)

final case class RoleSchemeRoleRow(
  roleSchemeId: Id[RoleScheme],
  roleId:       Id[Role]
)

final case class MemberRow(
  userId:      Id[User],
  workspaceId: Id[Workspace],
  roleId:      Id[Role],
  name:        Username,
  email:       Email,
  status:      MemberStatus,
  joined:      LocalDateTime,
  updated:     LocalDateTime
)

final case class WorkspaceRow(
  id:           Id[Workspace],
  name:         WorkspaceName,
  ownerId:      Id[User],
  roleSchemeId: Id[RoleScheme],
  created:      LocalDateTime,
  updated:      LocalDateTime
)

final case class LabelRow(
  id:   Id[Label],
  name: LabelName,
)

final case class TicketRow(
  id:          Id[Ticket],
  name:        TicketName,
  summary:     TicketSummary,
  description: Option[TicketDescription],
  reporterId:  Id[User],
  assigneeId:  Option[Id[User]],
  priority:    Priority,
  created:     LocalDateTime,
  updated:     LocalDateTime
)
