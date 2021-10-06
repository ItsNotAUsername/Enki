package com.github.enki
package domain
package permission

import workspace.Workspace

import cats.data.NonEmptyList

final case class Role(
  id:          Id[Role],
  name:        RoleName,
  system:      Boolean,
  workspace:   Option[Id[Workspace]],
  permissions: NonEmptyList[Permission]
)
