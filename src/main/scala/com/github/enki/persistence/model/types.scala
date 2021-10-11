package com.github.enki
package persistence
package model

import domain.*
import domain.permission.*
import domain.user.*
import domain.workspace.*

import java.time.LocalDateTime
import java.util.UUID

type UserRow = Id[User] *: Username *: Email *: HashedPassword *: Boolean *: UUID *: LocalDateTime *: LocalDateTime *: EmptyTuple

type PermissionRow = Id[Permission] *: PermissionName *: Scope *: EmptyTuple

type RoleRow = Id[Role] *: RoleName *: Boolean *: Option[Id[Workspace]] *: EmptyTuple

type RolePermissionRow = Id[Role] *: Id[Permission] *: EmptyTuple
