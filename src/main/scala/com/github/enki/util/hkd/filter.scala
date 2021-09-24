package com.github.enki
package util
package hkd

import cats.data.NonEmptyList
import doobie.{Fragment, Put}
import doobie.implicits.*
import doobie.util.fragments

private[hkd] type ApplyFilter[T] = FieldFilter[T]

trait FieldFilter[T]:
  def fragment: Fragment

final case class Empty[T]() extends FieldFilter[T]:
  def fragment: Fragment = Fragment.empty

final case class Between[T: Put](fieldName: String, left: T, right: T) extends FieldFilter[T]:
  def fragment: Fragment = fr"$fieldName BETWEEN $left AND $right"

final case class In[T: Put](fieldName: String, values: NonEmptyList[T]) extends FieldFilter[T]:
  def fragment: Fragment = fragments.in(fr"$fieldName", values)

final case class Like(fieldName: String, pattern: String) extends FieldFilter[String]:
  def fragment: Fragment = fr"$fieldName ILIKE $pattern"

final case class LessThan[T: Put](fieldName: String, value: T) extends FieldFilter[T]:
  def fragment: Fragment = fr"$fieldName < $value"

final case class GreaterThan[T: Put](fieldName: String, value: T) extends FieldFilter[T]:
  def fragment: Fragment = fr"$fieldName > $value"
