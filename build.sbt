lazy val CatsEffectVersion = "2.1.1"
lazy val Fs2Version        = "2.3.0"
lazy val Http4sVersion     = "0.21.3"
lazy val CirceVersion      = "0.13.0"
lazy val DoobieVersion     = "0.8.8"
lazy val PureconfigVersion = "0.12.3"
lazy val FlywayVersion     = "6.3.2"
lazy val LogbackVersion    = "1.2.3"
lazy val ScalaTestVersion  = "3.1.1"
lazy val ScalaCheckVersion = "1.14.3"

lazy val root = (project in file("."))
  .settings(
    organization := "hackathon",
    name := "hackatonxtao042020",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full),
    scalacOptions := Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-Ypartial-unification"
    ),
    libraryDependencies ++= Seq(
      "org.typelevel"   %% "cats-effect"         % CatsEffectVersion,
      "co.fs2"          %% "fs2-core"            % Fs2Version,
      "com.github.pureconfig" %% "pureconfig"    % PureconfigVersion,
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-core"          % CirceVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,

      "org.flywaydb"    %  "flyway-core"         % FlywayVersion,
      "org.tpolecat"    %% "doobie-core"         % DoobieVersion,
      "org.tpolecat"    %% "doobie-postgres"     % DoobieVersion,
      "org.tpolecat"    %% "doobie-quill"        % DoobieVersion,
      "org.tpolecat"    %% "doobie-hikari"       % DoobieVersion,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,

      "org.scalatest"   %% "scalatest"           % ScalaTestVersion  % Test,
      "org.scalacheck"  %% "scalacheck"          % ScalaCheckVersion % Test,
      "org.tpolecat"    %% "doobie-scalatest"    % DoobieVersion % Test
    )
  )

