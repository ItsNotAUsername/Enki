package com.github.enki
package persistence
package query

import domain.Id
import domain.permission.*

import doobie.*
import doobie.implicits.*

private[persistence] object PermissionQuery:

  def selectById(id: Id): Query0[Permission] =
    sql"SELECT * FROM permission WHERE id = $id"
      .query[Permission]

  def selectAll: Query0[Permission] =
    sql"SELECT * FROM permission"
      .query[Permission]

end PermissionQuery
