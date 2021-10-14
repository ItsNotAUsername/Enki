package com.github.enki
package http
package request

final case class CreateUserRequest(
  username: String,
  email:    String,
  password: String
)

final case class UpdateUserRequest(
  username: Option[String],
  email:    Option[String],
  password: Option[String]
)
