package com.github.enki
package util
package hkd

import domain.Id

type End = EmptyTuple

infix type +:[A, B] = B match
  case t *: ts => A *: B
  case _       => A *: B *: EmptyTuple

private[hkd] infix type contains[Ts <: Tuple, T] = Ts match
  case T *: ts => true
  case t *: ts => ts contains T
  case _       => false

private[hkd] type Reduce[T] = T match
  case List[t]    => List[Reduce[t]]
  case EntityF[s] => Id
  case _          => T

private[hkd] type ApplyRequired[C <: Creation, T] = C match
  case Required => T
  case Optional => Option[T]
