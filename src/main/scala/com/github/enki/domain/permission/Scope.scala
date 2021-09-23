package com.github.enki
package domain
package permission

import util.enums.*

import doobie.Meta
import doobie.postgres.implicits.pgEnumString

enum Scope extends SnakeCase:
  case Workspace, Project

object Scope:
  given Meta[Scope] = 
    pgEnumString(
      "scope", 
      snake2camel andThen Scope.valueOf, 
      _.toString
    )
