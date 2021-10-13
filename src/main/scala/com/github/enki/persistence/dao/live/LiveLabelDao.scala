package com.github.enki
package persistence
package dao
package live

import domain.{Id, LabelName}
import domain.ticket.Label
import mapping.*
import model.LabelRow
import model.meta.given

import cats.data.NonEmptyList as NEL
import doobie.ConnectionIO

class LiveLabelDao extends LabelDao:
  import query.LabelQuery

  def create(names: NEL[LabelName]): ConnectionIO[List[Label]] =
    LabelQuery
      .insert
      .updateManyWithGeneratedKeys[LabelRow]("id", "name")(names)
      .compile
      .toList
      .map(_.map(Label.fromRow))

  def findByIds(ids: NEL[Id[Label]]): ConnectionIO[List[Label]] =
    LabelQuery
      .selectByIds(ids)
      .to[List]
      .map(_.map(Label.fromRow))

end LiveLabelDao
