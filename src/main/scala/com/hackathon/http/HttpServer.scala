package com.hackathon.http

import cats.implicits._
import cats.data.ReaderT
import cats.effect.{Blocker, ContextShift, IO, Resource, Timer}
import com.hackathon.catsHttpTypes.ConfiguredResource
import com.hackathon.config.{Config, ServerConfig}
import com.hackathon.model
import com.hackathon.repository.UserRepository
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import org.http4s.server.{AuthMiddleware, Server}
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.server.blaze.BlazeServerBuilder

object HttpServer {

  def runHttpServer(implicit cs: ContextShift[IO],
                    time: Timer[IO]): ConfiguredResource[IO, Server[IO]] = {
    val routes: ConfiguredResource[IO, HttpRoutes[IO]] = ReaderT {
      config: Config =>
        implicit val httpErrorHandler: HttpErrorHandler[IO] = new HttpErrorHandler
        for {
          ce <- ExecutionContexts.fixedThreadPool[IO](5)
          te <- ExecutionContexts.cachedThreadPool[IO]
          xa <- HikariTransactor.newHikariTransactor[IO](
            config.postgres.driver,
            config.postgres.url,
            config.postgres.user,
            config.postgres.password,
            ce,
            Blocker.liftExecutionContext(te))
          oauth2Routes = new OAuth2Routes().routes
          userRepo: UserRepository = new UserRepository(xa)
          userAuthMiddleWare: AuthMiddleware[IO, model.User] = Authentication.userAuthMiddleWare(userRepo)
          userRoutes: AuthedRoutes[model.User, IO] = new UserRoutes(userRepo).authedUserRoutes
        } yield oauth2Routes <+> userAuthMiddleWare(userRoutes)
    }
    routes.flatMap(rs => server(rs))
  }

  def server(routes: HttpRoutes[IO])
            (implicit cs: ContextShift[IO],
                      timer: Timer[IO]): ConfiguredResource[IO, Server[IO]] = {
    ReaderT(config => server(config.server, routes))
  }

  def server(config: ServerConfig,
             routes: HttpRoutes[IO])
            (implicit cs: ContextShift[IO],
             timer: Timer[IO]): Resource[IO, Server[IO]] = {
    import org.http4s.implicits._
    BlazeServerBuilder[IO]
      .bindHttp(config.port, config.host)
      .withHttpApp(routes.orNotFound)
      .resource
  }
}