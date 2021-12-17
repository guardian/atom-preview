lazy val root = (project in file("."))
  .enablePlugins(PlayScala, RiffRaffArtifact, JDebPackaging)
  .settings(
    name := """play-scala-compile-di-example""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.6",
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
    scalacOptions ++= List(
      "-encoding", "utf8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xfatal-warnings"
    ),
     debianPackageDependencies := Seq("openjdk-8-jre-headless"),
      maintainer := "The Maintainer <the.maintainer@company.com>",
      packageSummary := "Brief description",
      packageDescription := """Slightly longer description""",
      riffRaffPackageType := (packageBin in Debian).value
  )
