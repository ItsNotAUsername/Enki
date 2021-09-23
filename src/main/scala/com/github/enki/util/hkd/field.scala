package com.github.enki
package util
package hkd

type Field[S <: State, C <: Creation, Ms <: Tuple, T] = S match
  case Create => ApplyCreate[Ms, C, T]
  case Entity => ApplyEntity[C, T]
  case Update => ApplyUpdate[Ms, C, T]
  case Filter => ApplyFilter[T]

sealed trait State
sealed trait Create extends State
sealed trait Entity extends State
sealed trait Update extends State
sealed trait Filter extends State

sealed trait Creation
sealed trait Required extends Creation
sealed trait Optional extends Creation

sealed trait Modifier
sealed trait Generated extends Modifier
sealed trait Immutable extends Modifier

sealed trait Skipped
object Skipped extends Skipped
