package com.github.enki
package http
package request

import cats.data.NonEmptyList

final case class CreateRoleSchemeRequest(
  name:  String,
  roles: NonEmptyList[Long]
)

final case class UpdateRoleSchemeRequest(
  name:        Option[String],
  addRoles:    Option[NonEmptyList[Long]],
  removeRoles: Option[NonEmptyList[Long]]
)
