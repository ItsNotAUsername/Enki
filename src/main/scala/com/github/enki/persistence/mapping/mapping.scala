package com.github.enki
package persistence
package mapping

import domain.permission.*
import domain.ticket.*
import domain.user.*
import domain.workspace.*
import model.*

import cats.data.NonEmptyList as NEL

extension (user: User)
  def toRow: UserRow = UserRow(
    user.id,
    user.username,
    user.email,
    user.password,
    user.active,
    user.code,
    user.created,
    user.updated
  )

extension (userObject: User.type)
  def fromRow(userRow: UserRow): User = User(
    userRow.id,
    userRow.username,
    userRow.email,
    userRow.password,
    userRow.active,
    userRow.code,
    userRow.created,
    userRow.updated
  )

extension (permissionObject: Permission.type)
  def fromRow(permissionRow: PermissionRow): Permission = Permission(
    permissionRow.id,
    permissionRow.name,
    permissionRow.scope
  )

extension (role: Role)
  def toRow: RoleRow = RoleRow(
    role.id,
    role.name,
    role.system,
    role.workspace
  )

extension (roleObject: Role.type)
  def fromRows(roleRow: RoleRow, permissions: NEL[Permission]): Role = Role(
    roleRow.id,
    roleRow.name,
    roleRow.system,
    roleRow.workspace,
    permissions
  )

extension (roleScheme: RoleScheme)
  def toRow: RoleSchemeRow = RoleSchemeRow(
    roleScheme.id,
    roleScheme.name,
    roleScheme.system,
    roleScheme.workspace
  )

extension (roleSchemeObject: RoleScheme.type)
  def fromRows(roleSchemeRow: RoleSchemeRow, roles: NEL[Role]): RoleScheme = RoleScheme(
    roleSchemeRow.id,
    roleSchemeRow.name,
    roleSchemeRow.system,
    roleSchemeRow.workspace,
    roles
  )

extension (member: Member)
  def toRow: MemberRow = MemberRow(
    member.userId,
    member.workspaceId,
    member.role.id,
    member.name,
    member.email,
    member.status,
    member.joined,
    member.updated
  )

extension (memberObject: Member.type)
  def fromRows(memberRow: MemberRow, role: Role): Member = Member(
    memberRow.userId,
    memberRow.workspaceId,
    memberRow.name,
    memberRow.email,
    role,
    memberRow.status,
    memberRow.joined,
    memberRow.updated
  )

extension (workspace: Workspace)
  def toRow: WorkspaceRow = WorkspaceRow(
    workspace.id,
    workspace.name,
    workspace.owner.userId,
    workspace.roleScheme.id,
    workspace.created,
    workspace.updated
  )

extension (workspaceObject: Workspace.type)
  def fromRows(
    workspaceRow: WorkspaceRow, 
    owner:        Member, 
    members:      NEL[Member], 
    roleScheme:   RoleScheme
  ): Workspace = Workspace(
    workspaceRow.id,
    workspaceRow.name,
    owner,
    members,
    roleScheme,
    workspaceRow.created,
    workspaceRow.updated
  )

extension (label: Label)
  def toRow: LabelRow = LabelRow(
    label.id,
    label.name
  )

extension (labelObject: Label.type)
  def fromRow(labelRow: LabelRow): Label = Label(
    labelRow.id,
    labelRow.name
  )

extension (ticket: Ticket)
  def toRow: TicketRow = TicketRow(
    ticket.id,
    ticket.name,
    ticket.summary,
    ticket.description,
    ticket.reporter.userId,
    ticket.assignee.map(_.userId),
    ticket.priority,
    ticket.created,
    ticket.updated
  )

extension (ticketObject: Ticket.type)
  def fromRows(
    ticketRow: TicketRow, 
    labels:    List[Label], 
    reporter:  Member, 
    assignee:  Option[Member]
  ): Ticket = Ticket(
    ticketRow.id,
    ticketRow.name,
    ticketRow.summary,
    ticketRow.description,
    labels,
    reporter,
    assignee,
    ticketRow.priority,
    ticketRow.created,
    ticketRow.updated
  )
