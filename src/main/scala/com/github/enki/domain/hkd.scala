package com.github.enki
package domain

sealed trait State
object State:
  sealed trait Create extends State
  sealed trait Get    extends State

type Reduce[T] = T match
  case Id                      => Unit
  case EntityF[f, s]           => Id
  case Iterable[EntityF[f, s]] => Seq[Id]
  case _                       => T

type Field[T, S <: State] = S match
  case State.Create => Reduce[T]
  case State.Get    => T

trait EntityF[F[_ <: State], S <: State]:
  val id: Field[Id, S]
