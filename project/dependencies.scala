import sbt._

object Versions {
  val cats = "2.6.1"
  val catsEffect = "3.2.7"
  val circe = "0.14.1"
  val doobie = "1.0.0-RC1"
  val flyway = "7.15.0"
  val http4s = "0.23.3"
  val monocle = "3.1.0"
  val munit = "1.0.5"
  val pureConfig = "0.16.0"
  val refined = "0.9.27"
  val tapir = "0.19.0-M8"
  val tsec = "0.4.0"
}

object Libraries {
  val cats = "org.typelevel" %% "cats-core" % Versions.cats
  val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect

  val circe = List(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-refined" 
  ).map(_ % Versions.circe)

  val doobie = List(
    "org.tpolecat" %% "doobie-core",
    "org.tpolecat" %% "doobie-hikari",
    "org.tpolecat" %% "doobie-postgres"
  ).map(_ % Versions.doobie)

  val flyway = "org.flywaydb" % "flyway-core" % Versions.flyway
  
  val http4s = List(
    "org.http4s" %% "http4s-dsl",
    "org.http4s" %% "http4s-circe",
    "org.http4s" %% "http4s-blaze-server"
  ).map(_ % Versions.http4s)

  val monocle = Seq(
    "dev.optics" %% "monocle-core",
    "dev.optics" %% "monocle-macro",
  ).map(_ % Versions.monocle)

  val munit = "org.typelevel" %% "munit-cats-effect-3" % Versions.munit % Test

  val pureConfig = "com.github.pureconfig" %% "pureconfig-core" % Versions.pureConfig

  val refined = "eu.timepit" %% "refined" % Versions.refined

  val tapir = List(
    "com.softwaremill.sttp.tapir" %% "tapir-core",
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe",
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server"
  ).map(_ % Versions.tapir)

  val tsec = "io.github.jmcardon" %% "tsec-password" % Versions.tsec
}
