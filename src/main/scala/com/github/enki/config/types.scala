package com.github.enki
package config

import util.newtype.RefinedNewtype

import eu.timepit.refined.predicates.all.*

type   NonEmptyProperty = NonEmptyProperty.Type
object NonEmptyProperty extends RefinedNewtype[String, NonEmpty]

type   PoolSize = PoolSize.Type
object PoolSize extends RefinedNewtype[Int, Interval.Closed[1, 20]]

type   Port = Port.Type
object Port extends RefinedNewtype[Int, Interval.Closed[0, 65535]]
