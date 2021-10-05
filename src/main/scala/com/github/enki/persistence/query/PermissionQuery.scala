package com.github.enki
package persistence
package query

import domain.Id
import domain.permission.Permission
import meta.given

import doobie.*
import doobie.implicits.*

private[persistence] object PermissionQuery:

  def selectById(id: Id[Permission]): Query0[Permission] =
    sql"""
      SELECT p.id, p.name, p.scope 
        FROM permission p 
       WHERE p.id = $id
    """.query[Permission]

  def selectAll: Query0[Permission] =
    sql"""
      SELECT p.id, p.name, p.scope 
        FROM permission p
    """.query[Permission]

end PermissionQuery
