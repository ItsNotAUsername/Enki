package com.github.enki
package util
package hkd

private[hkd] type ApplyCreate[Ms <: Tuple, C <: Creation, T] = Ms contains Generated match
  case true  => Skipped
  case false => ApplyRequired[C, Reduce[T]]
