import sbt._

object Versions {
  val cats = "2.6.1"
  val catsEffect = "3.2.7"
  val catsEffectTesting = "1.3.0"
  val circe = "0.14.1"
  val flyway = "7.15.0"
  val http4s = "0.23.3"
  val newtypes = "0.0.1"
  val scalaTest = "3.2.9"
  val skunk = "0.2.2"
  val tapir = "0.19.0-M8"
}

object Libraries {
  val cats = "org.typelevel" %% "cats-core" % Versions.cats
  val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect

  val circe = List(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-generic"
  ).map(_ % Versions.circe)

  val flyway = "org.flywaydb" % "flyway-core" % Versions.flyway
  
  val http4s = List(
    "org.http4s" %% "http4s-dsl",
    "org.http4s" %% "http4s-circe",
    "org.http4s" %% "http4s-blaze-server"
  ).map(_ % Versions.http4s)

  val newtypes = "io.monix" %% "newtypes-core" % Versions.newtypes

  val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalaTest % Test
  val catsEffectTesting = "org.typelevel" %% "cats-effect-testing-scalatest" % Versions.catsEffectTesting % Test

  val skunk = "org.tpolecat" %% "skunk-core" % Versions.skunk

  val tapir = List(
    "com.softwaremill.sttp.tapir" %% "tapir-core",
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe",
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server"
  ).map(_ % Versions.tapir)
}
