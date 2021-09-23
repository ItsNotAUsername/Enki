package com.github.enki
package util
package hkd

import domain.Id

private[hkd] type ApplyEntity[C <: Creation, T] = ApplyRequired[C, T]

trait EntityF[S <: State]:
  val id: Field[S, Required, Generated +: Immutable, Id]
