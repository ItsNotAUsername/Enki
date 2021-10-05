package com.github.enki
package persistence

import domain.Pagination

import doobie.{Fragment, Read, Query0}
import doobie.implicits.*

object SqlPagination:

  def paginate[A: Read](fr: Fragment, pagination: Pagination): Query0[A] =
    (fr ++ fr" OFFSET ${pagination.offset} LIMIT ${pagination.limit}").query[A]

end SqlPagination
