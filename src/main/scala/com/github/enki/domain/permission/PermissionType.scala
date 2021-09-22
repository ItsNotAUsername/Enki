package com.github.enki
package domain
package permission

import util.enums.*

import doobie.Meta
import doobie.postgres.implicits.pgEnumString

enum PermissionType extends SnakeCase:
  case Workspace, Project

object PermissionType:
  given Meta[PermissionType] = 
    pgEnumString(
      "permission_type", 
      snake2camel andThen PermissionType.valueOf, 
      _.toString
    )
