package com.github.enki
package persistence
package filter

import domain.{Id, Username}
import domain.permission.Permission
import domain.workspace.*

import cats.syntax.list.*
import doobie.Fragment
import doobie.implicits.*
import doobie.postgres.implicits.*
import doobie.util.fragments.{in, whereAndOpt}
import java.time.LocalDateTime

final case class MemberFilter(
  workspace:    Option[Id[Workspace]],
  name:         Option[Username],
  roleIds:      List[Id[Permission]],
  status:       Option[MemberStatus],
  joinedBefore: Option[LocalDateTime],
  joinedAfter:  Option[LocalDateTime]
) extends Filter:
  private val workspaceFragment    = workspace.map(id => fr"wm.workspace_id = $id")
  private val nameFragment         = name.map(n => fr"u.username ILIKE '%$n%'")
  private val rolesFragment        = roleIds.toNel.map(rs => in(fr"wm.role_id", rs))
  private val statusFragment       = status.map(s => fr"wm.status = $s")
  private val joinedBeforeFragment = joinedBefore.map(before => fr"wm.joined < $before")
  private val joinedAfterFragment  = joinedAfter.map(after => fr"wm.joined > $after")

  def fragment: Fragment = whereAndOpt(
    workspaceFragment,
    nameFragment,
    rolesFragment,
    statusFragment,
    joinedBeforeFragment,
    joinedAfterFragment
  )
