package com.github.enki
package config

import cats.effect.Sync
import pureconfig.{ConfigReader, ConfigSource}
import pureconfig.generic.derivation.default.*

final case class ServerConfig(
  host: NonEmptyProperty,
  port: Port
)

final case class DatabaseConfig(
  driver:   NonEmptyProperty,
  url:      NonEmptyProperty,
  user:     NonEmptyProperty,
  password: NonEmptyProperty,
  poolSize: PoolSize
)

final case class AppConfig(
  server:   ServerConfig, 
  database: DatabaseConfig
) derives ConfigReader

object AppConfig:
  def load[F[_]: Sync]: F[AppConfig] = 
    Sync[F].blocking(ConfigSource.default.loadOrThrow[AppConfig])
