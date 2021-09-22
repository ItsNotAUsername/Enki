package com.github.enki
package domain
package workspace

import permission.RoleSchemeF

import java.time.LocalDateTime

type Workspace       = WorkspaceF[State.Get]
type CreateWorkspace = WorkspaceF[State.Create]

final case class WorkspaceF[S <: State](
  id:         Field[Id, S],
  name:       Field[String, S],
  owner:      Field[MemberF[S], S],
  members:    Field[List[MemberF[S]], S],
  roleScheme: Field[RoleSchemeF[S], S],
  created:    Field[LocalDateTime, S],
  updated:    Field[LocalDateTime, S]
) extends EntityF[WorkspaceF, S]
