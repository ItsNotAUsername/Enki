package com.github.enki
package domain

import util.newtype.RefinedNewtype

import eu.timepit.refined.predicates.all.*

type NonEmptyWithMaxSize[N <: Int] = NonEmpty And MaxSize[N]

// User
type   Username = Username.Type
object Username extends RefinedNewtype[String, MinSize[3] And MaxSize[31]]

type   Email = Email.Type
object Email extends RefinedNewtype[String, MatchesRegex[".+@.+\\..+"] And MaxSize[255]]

type   PlainPassword = PlainPassword.Type
object PlainPassword extends RefinedNewtype[String, MinSize[8] And MaxSize[255]]

type   HashedPassword = HashedPassword.Type
object HashedPassword extends RefinedNewtype[String, NonEmptyWithMaxSize[255]]

// Permission
type   PermissionName = PermissionName.Type
object PermissionName extends RefinedNewtype[String, NonEmptyWithMaxSize[31]]

// Role
type   RoleName = RoleName.Type
object RoleName extends RefinedNewtype[String, NonEmptyWithMaxSize[31]]

// RoleScheme
type   RoleSchemeName = RoleSchemeName.Type
object RoleSchemeName extends RefinedNewtype[String, NonEmptyWithMaxSize[31]]

// Workspace
type   WorkspaceName = WorkspaceName.Type
object WorkspaceName extends RefinedNewtype[String, NonEmptyWithMaxSize[31]]

// Project
type   ProjectName = ProjectName.Type
object ProjectName extends RefinedNewtype[String, NonEmptyWithMaxSize[31]]

type   ProjectDescription = ProjectDescription.Type
object ProjectDescription extends RefinedNewtype[String, NonEmptyWithMaxSize[255]]

// Ticket
type   TicketName = TicketName.Type
object TicketName extends RefinedNewtype[String, NonEmptyWithMaxSize[255]]

type   TicketSummary = TicketSummary.Type
object TicketSummary extends RefinedNewtype[String, NonEmptyWithMaxSize[255]]

type   TicketDescription = TicketDescription.Type
object TicketDescription extends RefinedNewtype[String, NonEmptyWithMaxSize[65535]]

// Label
type   LabelName = LabelName.Type
object LabelName extends RefinedNewtype[String, NonEmptyWithMaxSize[31]]

// Pagination
type   Limit = Limit.Type
object Limit extends RefinedNewtype[Int, Interval.Closed[1, 50]]

type   Offset = Offset.Type
object Offset extends RefinedNewtype[Int, GreaterEqual[0]]
