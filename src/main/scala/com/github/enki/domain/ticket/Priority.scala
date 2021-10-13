package com.github.enki
package domain
package ticket

import util.enums.*

import doobie.Meta
import doobie.postgres.implicits.pgEnumString

enum Priority extends SnakeCase:
  case Highest, High, Medium, Low, Lowest

object Priority:
  given Meta[Priority] =
    pgEnumString(
      "ticket_priority", 
      snake2camel andThen Priority.valueOf, 
      _.toString
    )

