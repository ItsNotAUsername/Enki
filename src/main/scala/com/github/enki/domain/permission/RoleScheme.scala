package com.github.enki
package domain
package permission

import workspace.Workspace

import cats.data.NonEmptyList

final case class RoleScheme(
  id:        Id[RoleScheme],
  name:      RoleSchemeName,
  system:    Boolean,
  workspace: Option[Id[Workspace]],
  roles:     NonEmptyList[Role]
)
