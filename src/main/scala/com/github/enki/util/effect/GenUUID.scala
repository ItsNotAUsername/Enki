package com.github.enki
package util
package effect

import cats.effect.Sync
import java.util.UUID

trait GenUUID[F[_]]:
  def uuid: F[UUID]

object GenUUID:

  def apply[F[_]](using ev: GenUUID[F]): GenUUID[F] = ev

  given genUuid4Sync[F[_]](using F: Sync[F]): GenUUID[F] with
    def uuid: F[UUID] = F.delay(UUID.randomUUID())

end GenUUID
