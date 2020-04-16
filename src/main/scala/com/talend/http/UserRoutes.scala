package com.talend.http

import cats.effect._
import cats.syntax.all._
import com.talend.model._
import com.talend.repository.UserRepository
import com.talend.validation.UserValidation
import org.http4s.circe.CirceEntityCodec._
import io.circe.generic.auto._
import org.http4s._
import org.http4s.dsl.Http4sDsl

class UserRoutes(userRepository: UserRepository) extends Http4sDsl[IO] {
  val authedUserRoutes: AuthedRoutes[User, IO] = {
    AuthedRoutes.of[User, IO] {
      case GET -> Root / "user" / "info" as user =>
        userRepository.get(user.id).flatMap {
          case Some(u) => Ok(u)
          case _ => InternalServerError("Can't find user")
        }
      case POST -> Root / "user" / "new" as user =>
        userRepository.upsert(user) >> Created()
    }
  }

}

