import Libraries._

ThisBuild / organization := "com.github.itsnotausername"
ThisBuild / scalaVersion := "3.0.1"
ThisBuild / version      := "0.1.0"

lazy val dependencies = 
  List(cats, catsEffect, catsEffectTesting, flyway, newtypes, scalaTest, skunk) ++ circe ++ http4s ++ tapir

lazy val root = (project in file("."))
  .settings(
    name := "enki",
    libraryDependencies ++= dependencies
  )
