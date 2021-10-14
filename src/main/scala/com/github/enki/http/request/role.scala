package com.github.enki
package http
package request

import cats.data.NonEmptyList

final case class CreateRoleRequest(
  name:        String,
  permissions: NonEmptyList[Long]
)

final case class UpdateRoleRequest(
  name:              Option[String],
  addPermissions:    Option[NonEmptyList[Long]],
  removePermissions: Option[NonEmptyList[Long]]
)
