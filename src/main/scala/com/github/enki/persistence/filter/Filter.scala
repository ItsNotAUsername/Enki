package com.github.enki
package persistence
package filter

import doobie.Fragment

trait Filter:
  def fragment: Fragment
