import sbt._

object Versions {
  val enumeratum = "1.6.1"
  val http4s     = "0.21.23"
  val refined    = "0.9.25"
  val tapir      = "0.18.0-M11"
  val zio        = "1.0.8"
  val zioJson    = "0.1.5"
}

object Libraries {
  def tapir(artifactName: String): ModuleID =
    "com.softwaremill.sttp.tapir" %% s"tapir-$artifactName" % Versions.tapir

  def http4s(artifactName: String): ModuleID =
    "org.http4s" %% s"http4s-$artifactName" % Versions.http4s

  // Zio
  val zio = "dev.zio" %% "zio" % Versions.zio

  // Json
  val zioJson = "dev.zio" %% "zio-json" % Versions.zioJson

  // Tapir
  val tapirCore       = tapir("core")
  val tapirEnumeratum = tapir("enumeratum")
  val tapirRefined    = tapir("refined")
  val tapirZio        = tapir("zio")
  val tapirZioHttp4s  = tapir("zio-http4s-server")
  val tapirZioJson    = tapir("json-zio")

  // Enumeratum
  val enumeratum = "com.beachape" %% "enumeratum" % Versions.enumeratum

  // Refined
  val refined = "eu.timepit" %% "refined" % Versions.refined

  // Http4s
  val http4sDsl    = http4s("dsl")
  val http4sServer = http4s("blaze-server")

  // Test
  val zioTest = "dev.zio" %% "zio-test" % Versions.zio % Test
}
