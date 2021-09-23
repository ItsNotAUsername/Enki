package com.github.enki
package util
package hkd

enum UpdateField[T]:
  case UpdateSingle(set: T)
  case UpdateMany(add: List[T], delete: List[T])

private[hkd] type ApplyUpdate[Ms <: Tuple, C <: Creation, T] = Ms contains Immutable match
  case true  => Skipped
  case false => Option[ChooseUpdate[C, T]]

private[hkd] type ChooseUpdate[C <: Creation, T] = T match
  case List[t] => UpdateField.UpdateMany[Reduce[t]]
  case _       => UpdateField.UpdateSingle[ApplyRequired[C, Reduce[T]]]
