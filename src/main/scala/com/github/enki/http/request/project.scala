package com.github.enki
package http
package request

import cats.data.NonEmptyList

final case class CreateProjectRequest(
  name:        String,
  description: String,
  lead:        Long,
  members:     List[Long]
)

final case class UpdateProjectRequest(
  name:          Option[String],
  description:   Option[String],
  lead:          Option[Long],
  addMembers:    Option[NonEmptyList[Long]],
  removeMembers: Option[NonEmptyList[Long]]
)
