package com.github.enki
package persistence
package filter

import domain.*
import domain.ticket.{Label, Priority}

import doobie.Fragment
import doobie.implicits.*
import doobie.postgres.implicits.*
import doobie.util.fragments.andOpt
import java.time.LocalDateTime

final case class TicketFilter(
  name:          Option[TicketName],
  priority:      Option[Priority],
  createdBefore: Option[LocalDateTime],
  createdAfter:  Option[LocalDateTime]
) extends Filter:
  private val nameFragment          = name.map(n => fr"t.name ILIKE '%$n%'")
  private val priorityFragment      = priority.map(p => fr"t.priority = $p")
  private val createdBeforeFragment = createdBefore.map(before => fr"t.created < $before")
  private val createdAfterFragment  = createdAfter.map(after => fr"t.created > $after")

  def fragment: Fragment = andOpt(
    nameFragment,
    priorityFragment,
    createdBeforeFragment, 
    createdAfterFragment
  )
