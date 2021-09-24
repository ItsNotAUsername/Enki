package com.github.enki
package repository

import domain.Id
import domain.permission.*
import persistence.repository.impl.LivePermissionRepository

import cats.effect.{IO, Resource}

class PermissionRepositorySpec extends DBSuite:

  val permissionRepo = ResourceSuiteLocalFixture(
    "permission_repo",
    transactor.map(LivePermissionRepository(_))
  )

  override def munitFixtures = List(permissionRepo)

  test("call findPermissionById should return Some(perm) if permission with given id exists") {
    permissionRepo().findPermissionById(Id(1))
      .map(_.get.name)
      .assertEquals("create_project")
  }

  test("call findPermissionById should return None if permission with given id doesn't exist") {
    permissionRepo().findPermissionById(Id(5))
      .map(_.isEmpty)
      .assert
  }

  test("call findAllPermssions should return a list of all existing permissions") {
    permissionRepo().findAllPermissions
      .map(_.size)
      .assertEquals(2)
  }

end PermissionRepositorySpec
