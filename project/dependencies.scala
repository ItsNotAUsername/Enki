import sbt._

object Versions {
  val http4s  = "0.21.22"
  val tapir   = "0.18.0-M10"
  val zio     = "1.0.7"
  val zioJson = "0.1.4"
}

object Libraries {
  def tapir(artifactName: String): ModuleID =
    "com.softwaremill.sttp.tapir" %% s"tapir-$artifactName" % Versions.tapir

  def http4s(artifactName: String): ModuleID =
    "org.http4s" %% s"http4s-$artifactName" % Versions.http4s

  val zio = "dev.zio" %% "zio" % Versions.zio

  val zioJson = "dev.zio" %% "zio-json" % Versions.zioJson

  val tapirCore      = tapir("core")
  val tapirZio       = tapir("zio")
  val tapirZioJson   = tapir("json-zio")
  val tapirZioHttp4s = tapir("zio-http4s-server")

  val http4sDsl    = http4s("dsl")
  val http4sServer = http4s("blaze-server")

  val zioTest = "dev.zio" %% "zio-test" % Versions.zio % Test
}
