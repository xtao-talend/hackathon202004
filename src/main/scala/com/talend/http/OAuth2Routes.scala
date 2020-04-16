package com.talend.http

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import cats.implicits._

class OAuth2Routes(implicit errorHandler: HttpErrorHandler[IO]) extends Http4sDsl[IO] {
  val routes: HttpRoutes[IO] = HttpRoutes.of {
    case GET -> Root / "oauth2" / "callback" :? Code(code) => Ok(code)
  }

  object Code extends QueryParamDecoderMatcher[String]("code")
}
