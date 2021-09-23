package com.github.enki

import cats.effect.{IO, Resource}
import doobie.*
import doobie.implicits.*
import fs2.io.file.{Files, Path}
import munit.CatsEffectSuite

trait DBSuite extends CatsEffectSuite:

  private val file: Path = Path("""src\test\resources\init.sql""")

  private val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost:5432/enki_test",
    "postgres",
    "postgres"
  )

  def transactor: Resource[IO, Transactor[IO]] =
    Resource.make {
      (dropTables(xa) >> createTables(file, xa)).as(xa)
    } {
      _ => IO.unit
    }

  private def createTables(file: Path, xa: Transactor[IO]): IO[Unit] =
    Files[IO].readAll(file)
      .through(fs2.text.utf8.decode)
      .compile
      .foldMonoid
      .map(Fragment.const(_))
      .flatMap(_.update.run.transact(xa))
      .void

  private def dropTables(xa: Transactor[IO]): IO[Unit] =
    sql"DROP TABLE usr".update
      .run
      .transact(xa)
      .void

end DBSuite
