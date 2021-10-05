package com.github.enki
package domain
package permission

final case class Permission(
  id:    Id[Permission],
  name:  PermissionName,
  scope: Scope
)
