package com.github.enki
package domain
package project

import workspace.MemberF

import java.time.LocalDateTime

type Project       = ProjectF[State.Get]
type CreateProject = ProjectF[State.Create]

final case class ProjectF[S <: State](
  id:          Field[Id, S],
  name:        Field[String, S],
  description: Field[String, S],
  lead:        Field[MemberF[S], S],
  created:     Field[LocalDateTime, S],
  updated:     Field[LocalDateTime, S]
) extends EntityF[ProjectF, S]
