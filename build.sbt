import Libraries._

ThisBuild / organization := "com.github.itsnotausername"
ThisBuild / scalaVersion := "3.1.0-RC2"
ThisBuild / version      := "0.1.0"

lazy val dependencies = 
  List(cats, catsEffect, clearConfig, flyway, munit, refined, tsec) ++ 
    circe ++ doobie ++ http4s ++ monocle ++ tapir

lazy val root = (project in file("."))
  .settings(
    name := "enki",
    libraryDependencies ++= dependencies
  )
