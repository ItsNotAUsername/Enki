package com.github.enki
package domain

final case class Pagination(offset: Offset, limit: Limit)

object Pagination:

  private val defaultOffset: Offset = Offset(0)
  private val defaultLimit: Limit   = Limit(20)

  def from(offset: Option[Offset], limit: Option[Limit]): Pagination =
    Pagination(
      offset.getOrElse(defaultOffset),
      limit.getOrElse(defaultLimit)
    )

  val default: Pagination = Pagination(defaultOffset, defaultLimit)

end Pagination
