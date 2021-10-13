package com.github.enki
package persistence
package query

import domain.{Id, LabelName}
import domain.ticket.Label
import model.LabelRow
import model.meta.given

import cats.data.NonEmptyList as NEL
import doobie.{Query0, Update}
import doobie.implicits.*
import doobie.util.fragments.{in, whereAnd}

private[persistence] object LabelQuery:

  private val selectFragment = fr"""
    SELECT l.id, l.name
      FROM label l
  """

  def insert: Update[LabelName] =
    val sql = """
      INSERT INTO label (name) 
           VALUES (?) 
      ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name
    """
    Update[LabelName](sql)

  def selectByIds(ids: NEL[Id[Label]]): Query0[LabelRow] =
    (selectFragment ++ whereAnd(in(fr"l.id", ids)))
      .query[LabelRow]

end LabelQuery
