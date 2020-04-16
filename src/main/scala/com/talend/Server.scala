package com.talend

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.functor._
import com.talend.config.Config
import com.talend.http.HttpServer
import org.flywaydb.core.Flyway
import pureconfig.ConfigSource

// The only place where the Effect is defined. You could change it for `TaskApp` and `monix.eval.Task` for example.
object Server extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- IO(ConfigSource.default.loadOrThrow[Config])
      flyway = Flyway
        .configure()
        .dataSource(
          config.postgres.url,
          config.postgres.user,
          config.postgres.password
        ).load()
      _ <- IO(flyway.migrate())
      r <- HttpServer.runHttpServer
          .run(config)
          .use(_ => IO.never)
          .as(ExitCode.Success)
    } yield r
  }

}
