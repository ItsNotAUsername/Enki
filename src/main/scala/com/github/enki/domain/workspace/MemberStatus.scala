package com.github.enki
package domain
package workspace

import util.enums.*

import doobie.Meta
import doobie.postgres.implicits.pgEnumString

enum MemberStatus extends SnakeCase:
  case Pending, Approved

object MemberStatus:
  given Meta[MemberStatus] =
    pgEnumString(
      "member_status",
      snake2camel andThen MemberStatus.valueOf,
      _.toString
    )
