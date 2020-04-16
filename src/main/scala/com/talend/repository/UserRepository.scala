package com.talend.repository

import cats.effect.IO
import com.talend.model._
import doobie.util.transactor.Transactor

class UserRepository(transactor: Transactor[IO]) {
  import dbPostgres._

  def get(id: String): IO[Option[User]] = ???

  def upsert(user: User): IO[Long] = ???

  private val postgresUserRepository = quote(query[User])
}

