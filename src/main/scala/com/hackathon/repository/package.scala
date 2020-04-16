package com.hackathon

import doobie.quill.DoobieContext
import io.getquill.SnakeCase

package object repository {
  val dbPostgres = new DoobieContext.Postgres(SnakeCase)
}
