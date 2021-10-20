package com.github.enki
package persistence
package filter

import domain.{Id, RoleName}
import domain.workspace.Workspace

import doobie.Fragment
import doobie.implicits.*
import doobie.util.fragments.whereAndOpt

final case class RoleFilter(
  name:      Option[RoleName]      = None,
  workspace: Option[Id[Workspace]] = None
) extends Filter:
  private val nameFragment      = name.map(n => fr"r.name ILIKE '%$n%'")
  private val workspaceFragment = workspace.map(id => fr"r.workspace_id = $id")

  def fragment: Fragment = whereAndOpt(nameFragment, workspaceFragment)