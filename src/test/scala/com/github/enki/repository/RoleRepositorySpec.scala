package com.github.enki
package repository

import domain.*
import domain.permission.{Permission, Role, Scope}
import persistence.filter.RoleFilter
import persistence.repository.impl.LiveRoleRepository

import cats.data.NonEmptyList
import cats.effect.{IO, Resource}
import cats.syntax.option.*

class RoleRepositorySpec extends DBSuite:

  val roleRepo = ResourceSuiteLocalFixture(
    "role_repo",
    transactor.map(LiveRoleRepository(_))
  )

  override def munitFixtures = List(roleRepo)

  test("call createRole should create new role") {
    val role = Role(
      Id.undefined, 
      RoleName("New role"), 
      false, 
      Id(1).some, 
      NonEmptyList.of(
        Permission(Id(1), PermissionName("create_project"), Scope.Workspace)
      )
    )

    (
      for
        role   <- roleRepo().createRole(role)
        mbRole <- roleRepo().findRoleById(role.id)
      yield mbRole
    ).map(_.get.name.value).assertEquals("New role")
  }

  test("call updateRole should update existing role") {
    val role = Role(
      Id(1), 
      RoleName("admin"), 
      true,
      none, 
      NonEmptyList.of(
        Permission(Id(1), PermissionName("create_project"), Scope.Workspace)
      )
    )

    (
      for
        _      <- roleRepo().updateRole(role)
        mbRole <- roleRepo().findRoleById(Id(1))
      yield mbRole
    ).map(_.get).assertEquals(role)
  }

  test("call findRoleById should return Some(role) if role with given id exists") {
    roleRepo().findRoleById(Id(1))
      .map(_.get.name.value)
      .assertEquals("admin")
  }

  test("call findRoleById should return None if role with given id doesn't exist") {
    roleRepo().findRoleById(Id(10))
      .map(_.isEmpty)
      .assert
  }

  test("call findRolesByIds should return list of roles with given ids") {
    roleRepo().findRolesByIds(NonEmptyList.of(Id(1), Id(2)))
      .map(_.length)
      .assertEquals(2)
  }

  test("call findRolesByFilter should return list of roles that satisfy filter conditions") {
    val filter1 = RoleFilter(RoleName("admin").some, none)
    val filter2 = RoleFilter(none, Id(1).some)
    val filter3 = RoleFilter()

    roleRepo().findRolesByFilter(filter1)
      .map(_.length)
      .assertEquals(1)

    roleRepo().findRolesByFilter(filter2)
      .map(_.length)
      .assertEquals(1)

    roleRepo().findRolesByFilter(filter3)
      .map(_.length)
      .assertEquals(2)
  }

  test("call deleteRole should delete role with given id") {
    (
      for
        _      <- roleRepo().deleteRole(Id(2))
        mbRole <- roleRepo().findRoleById(Id(2))
      yield mbRole
    ).map(_.isEmpty).assert
  }

end RoleRepositorySpec
