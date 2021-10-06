package com.github.enki
package domain
package project

import workspace.{Member, Workspace}

import cats.data.NonEmptyList
import java.time.LocalDateTime

final case class Project(
  id:          Id[Project],
  workspaceId: Id[Workspace],
  name:        ProjectName,
  description: ProjectDescription,
  lead:        Member,
  members:     NonEmptyList[Member],
  created:     LocalDateTime,
  updated:     LocalDateTime
)
