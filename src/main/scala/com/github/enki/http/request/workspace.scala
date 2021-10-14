package com.github.enki
package http
package request

import cats.data.NonEmptyList

final case class CreateWorkspaceRequest(name: String)

final case class UpdateWorkspaceRequest(
  name:          Option[String],
  addMembers:    Option[NonEmptyList[Long]],
  removeMembers: Option[NonEmptyList[Long]],
  roleScheme:    Option[Long]
)
