package com.talend.http

import cats.implicits._
import cats.data.{Kleisli, OptionT}
import cats.effect.IO
import com.talend.model.User
import com.talend.repository.UserRepository
import org.http4s.{Header, Request}
import org.http4s.server.AuthMiddleware
import org.http4s.util.CaseInsensitiveString

object Authentication {
  val USER_HEADER = "user-header"

  def userAuth(userRepository: UserRepository): Kleisli[OptionT[IO, *], Request[IO], User] = {
    Kleisli { request =>
      val userHeaderOpt: Option[Header] =
        request.headers.get(CaseInsensitiveString(USER_HEADER))
      val userIdOpt: Option[String] =
        userHeaderOpt.map(_.toString)
      val userOpt: IO[Option[User]] =
        userIdOpt.flatTraverse(userRepository.get)
      OptionT(userOpt)
    }
  }

  def userAuthMiddleWare(userRepository: UserRepository): AuthMiddleware[IO, User] = {
    AuthMiddleware(userAuth(userRepository))
  }
}
