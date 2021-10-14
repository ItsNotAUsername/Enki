package com.github.enki
package http
package request

import cats.data.NonEmptyList

final case class CreateTicketRequest(
  name:        String,
  summary:     String,
  description: Option[String],
  labels:      List[String],
  assignee:    Option[Long]
)

final case class UpdateTicketRequest(
  name:         Option[String],
  summary:      Option[String],
  description:  Option[Option[String]],
  addLabels:    Option[NonEmptyList[String]],
  removeLabels: Option[NonEmptyList[String]],
  assignee:     Option[Option[Long]]
)
