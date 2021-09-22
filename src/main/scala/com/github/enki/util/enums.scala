package com.github.enki.util

import scala.util.matching.Regex

object enums:

  // from Enumeratum: https://github.com/lloydmeta/enumeratum/blob/master/enumeratum-core/src/main/scala/enumeratum/EnumEntry.scala

  private val regex1 = "([A-Z]+)([A-Za-z])".r
  private val regex2 = "([a-z\\d])([A-Z])".r
  private val regex3 = "_+".r
  private val rep    = "$1_$2"

  def capitalise(str: String): String = str.take(1).toUpperCase + str.drop(1)

  def camel2snake(str: String): String =
    val fst = regex1.replaceAllIn(str, rep)
    regex2.replaceAllIn(str, rep).toLowerCase

  def snake2camel(str: String): String =
    regex3.split(str).map(capitalise).mkString

  transparent trait SnakeCase:
    override def toString: String = camel2snake(super.toString)

end enums
