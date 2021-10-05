package com.github.enki
package repository

import domain.*
import domain.user.User
import persistence.repository.impl.LiveUserRepository
import util.typeclass.{GenUUID, Time}

import cats.effect.{IO, Resource}
import cats.syntax.option.*
import java.time.LocalDateTime
import java.util.UUID

class UserRepositorySpec extends DBSuite:

  val userRepo = ResourceSuiteLocalFixture(
    "userRepo",
    transactor.map(LiveUserRepository(_))
  )

  override def munitFixtures = List(userRepo)

  test("call createUser should create new User") {
    val action = for
      now    <- Time[IO].localDateTime
      code   <- GenUUID[IO].uuid
      create  = User(
                  Id.undefined,
                  Username("Zhora"),
                  Email("zhora@gmail.com"),
                  HashedPassword("pAsSwOrD"),
                  false,
                  code,
                  now,
                  now
                )
      user   <- userRepo().createUser(create)
      mbUser <- userRepo().findUserById(Id(3))
    yield mbUser

    action
      .map(_.get.username.value)
      .assertEquals("Zhora")
  }

  test("call updateUser should update existing user") {
    val action = for
      now     <- Time[IO].localDateTime
      code    <- GenUUID[IO].uuid
      user     = User(
                   Id(2),
                   Username("Sasha"),
                   Email("sasha@gmail.com"),
                   HashedPassword("MORE PAWAH"),
                   true,
                   code,
                   now,
                   now
                 )
      _       <- userRepo().updateUser(user)
      updated <- userRepo().findUserById(Id(2))
    yield updated

    action
      .map(_.get.password.value)
      .assertEquals("MORE PAWAH")
  }

  test("call findUserById should return Some(user) if user with given id exists") {
    userRepo()
      .findUserById(Id(1))
      .map(_.get.username.value)
      .assertEquals("Nikita")
  }

  test("call findUserById should return None if user with given id doesn't exist") {
    userRepo()
      .findUserById(Id(4))
      .map(_.isEmpty)
      .assert
  }

  test("call findUserByEmail should return Some(user) if user with given email exists") {
    userRepo()
      .findUserByEmail(Email("nikita@yandex.com"))
      .map(_.get.username.value)
      .assertEquals("Nikita")
  }

  test("call findUserByEmail should return None if user with given email doesn't exist") {
    userRepo()
      .findUserByEmail(Email("abc@def.ru"))
      .map(_.isEmpty)
      .assert
  }

  test("call findUserByCode should return Some(user) if user with given code exists") {
    userRepo()
      .findUserByCode(UUID.fromString("9e057b2a-a9f4-4e7c-b62a-4a62e8e3243e"))
      .map(_.get.username.value)
      .assertEquals("Nikita")
  }

  test("call findUserByCode should return None if user with given code doesn't exist") {
    val action = for
      code <- GenUUID[IO].uuid
      user <- userRepo().findUserByCode(code)
    yield user

    action.map(_.isEmpty)
      .assert
  }

end UserRepositorySpec
