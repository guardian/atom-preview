lazy val root = (project in file("."))
  .enablePlugins(PlayScala, RiffRaffArtifact, JDebPackaging)
  .settings(
    name := """atom-preview""",
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
      riffRaffPackageType := (packageBin in Debian).value,
    riffRaffArtifactResources := Seq(
      (Debian / packageBin).value -> s"${name.value}/${name.value}.deb",
      baseDirectory.value / "riff-raff.yaml" -> "riff-raff.yaml",
      baseDirectory.value / "cdk" / "cdk.out" / "AtomPreview.template.json" -> s"cloudformation/AtomPreview.template.json"
    ),
  )
