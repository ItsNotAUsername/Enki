package com.github.enki
package persistence

import cats.effect.{Async, Resource}
import cats.syntax.functor.*
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import org.flywaydb.core.Flyway

object Database:
  
  // def transactor[F[_]: Async](config: DatabaseConfig): Resource[F, HikariTransactor[F]] = 
  //   for 
  //     ce <- ExecutionContexts.fixedThreadPool[F](config.poolSize.value)
  //     xa <- HikariTransactor.newHikariTransactor[F](
  //             config.driver.value,
  //             config.url.value,
  //             config.user.value,
  //             config.password.value,
  //             ce
  //           )
  //   yield xa

  def migrate[F[_]: Async](transactor: HikariTransactor[F]): F[Unit] =
    transactor.configure { src =>
      Async[F].delay(
        Flyway.configure()
          .dataSource(src)
          .load()
          .migrate()
      ).void
    }

end Database
