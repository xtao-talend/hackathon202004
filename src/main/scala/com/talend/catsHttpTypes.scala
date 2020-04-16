package com.talend

import cats.data.ReaderT
import cats.effect.Resource
import com.talend.config.Config

object catsHttpTypes {
  type Configured[F[_], T] = ReaderT[F, Config, T]
  type ConfiguredResource[F[_], T] = Configured[Resource[F, ?], T]
}
