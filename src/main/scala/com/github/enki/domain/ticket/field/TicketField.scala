package com.github.enki
package domain
package ticket
package field

import workspace.MemberF

import java.time.{LocalDate, LocalDateTime}

enum TicketFieldF[S <: State]:
  type FieldType

  def name:  String
  def value: Field[FieldType, S]

  case ShortTextField[S <: State](name: String, value: Field[String, S]) extends TicketFieldF[S], Helper[String]
  case TextField[S <: State](name: String, value: Field[String, S]) extends TicketFieldF[S], Helper[String]
  case MemberField[S <: State](name: String, value: Field[MemberF[S], S]) extends TicketFieldF[S], Helper[MemberF[S]]
  case PriorityField[S <: State](name: String, value: Field[Priority, S]) extends TicketFieldF[S], Helper[Priority]
  case DateField[S <: State](name: String, value: Field[LocalDate, S]) extends TicketFieldF[S], Helper[LocalDate]
  case DateTimeField[S <: State](name: String, value: Field[LocalDateTime, S]) extends TicketFieldF[S], Helper[LocalDateTime]
  case IntegerField[S <: State](name: String, value: Field[Int, S]) extends TicketFieldF[S], Helper[Int]
  case DecimalField[S <: State](name: String, value: Field[Double, S]) extends TicketFieldF[S], Helper[Double]
