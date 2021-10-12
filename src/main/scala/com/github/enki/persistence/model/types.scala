package com.github.enki
package persistence
package model

import domain.*
import domain.permission.*
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
