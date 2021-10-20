package com.github.enki
package persistence
package filter

import domain.{Id, RoleSchemeName}
import domain.workspace.Workspace

import doobie.Fragment
import doobie.implicits.*
import doobie.util.fragments.whereAndOpt

final case class RoleSchemeFilter(
  name:      Option[RoleSchemeName],
  workspace: Option[Id[Workspace]]
) extends Filter:
  private val nameFragment      = name.map(n => fr"rs.name ILIKE '%$n%'")
  private val workspaceFragment = workspace.map(id => fr"rs.workspace_id = $id")
  
  def fragment: Fragment = whereAndOpt(nameFragment, workspaceFragment)
