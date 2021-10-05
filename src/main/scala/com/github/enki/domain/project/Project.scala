package com.github.enki
package domain
package project

import workspace.{Member, Workspace}

import java.time.LocalDateTime

final case class Project(
  id:          Id[Project],
  workspaceId: Id[Workspace],
  name:        ProjectName,
  description: ProjectDescription,
  lead:        Member,
  members:     List[Member],
  created:     LocalDateTime,
  updated:     LocalDateTime
)
