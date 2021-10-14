package com.github.enki
package config

import util.newtype.Newtype

import eu.timepit.refined.predicates.all.*

type   NonEmptyProperty = NonEmptyProperty.Type
object NonEmptyProperty extends Newtype[String, NonEmpty]

type   PoolSize = PoolSize.Type
object PoolSize extends Newtype[Int, Interval.Closed[1, 20]]

type   Port = Port.Type
object Port extends Newtype[Int, Interval.Closed[0, 65535]]
