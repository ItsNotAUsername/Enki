ThisBuild / scalaVersion := "2.13.6"
ThisBuild / version      := "0.1.0"
ThisBuild / organization := "com.github.itsnotausername"

lazy val root = (project in file("."))
  .settings(
    name := "enki",
    libraryDependencies ++= Seq(
      Libraries.zio,
      Libraries.zioJson,
      Libraries.zioTest,
      Libraries.enumeratum,
      Libraries.refined,
      Libraries.tapirCore,
      Libraries.tapirEnumeratum,
      Libraries.tapirRefined,
      Libraries.tapirZio,
      Libraries.tapirZioHttp4s,
      Libraries.tapirZioJson,
      Libraries.http4sDsl,
      Libraries.http4sServer
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
