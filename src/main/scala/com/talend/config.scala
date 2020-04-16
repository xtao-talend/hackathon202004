package com.talend

import pureconfig.ConfigReader
import pureconfig.generic.semiauto._
import pureconfig.generic.DerivedConfigReader._

object config {
  case class Config(postgres: PostgresConfig, server: ServerConfig)

  case class ServerConfig(host: String,
                          port: Int)

  case class PostgresConfig(url: String,
                            user: String,
                            password: String) {
    def driver = "org.postgresql.Driver"
  }

  object Config {
    implicit val configReader: ConfigReader[Config] = deriveReader[Config]
  }
}
