package com.github.enki
package persistence
package dao

import domain.{Id, LabelName}
import domain.ticket.Label

import cats.data.NonEmptyList as NEL
import doobie.ConnectionIO

trait LabelDao:
  def create(names: NEL[LabelName]): ConnectionIO[List[Label]]
  def findByIds(ids: NEL[Id[Label]]): ConnectionIO[List[Label]]
