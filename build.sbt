lazy val root = (project in file("."))
  .enablePlugins(PlayScala, RiffRaffArtifact, JDebPackaging, SystemdPlugin)
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
      maintainer := "The Maintainer <the.maintainer@company.com>",
      packageSummary := "Brief description",
      packageDescription := """Slightly longer description""",
      riffRaffPackageType := (packageBin in Debian).value,
    riffRaffArtifactResources := Seq(
      (Debian / packageBin).value -> s"${name.value}/${name.value}.deb",
      baseDirectory.value / "riff-raff.yaml" -> "riff-raff.yaml",
      baseDirectory.value / "cdk" / "cdk.out" / "AtomPreview-CODE.template.json" -> s"cloudformation/AtomPreview-CODE.template.json",
      baseDirectory.value / "cdk" / "cdk.out" / "AtomPreview-PROD.template.json" -> s"cloudformation/AtomPreview-PROD.template.json"

  ),
    Universal / javaOptions ++= Seq(
      s"-Dpidfile.path=/dev/null",
      s"-J-Dlogs.home=/var/log/${packageName.value}",
      s"-J-Xloggc:/var/log/${packageName.value}/gc.log",
    ),
      libraryDependencies ++= Seq(
          "com.gu" %% "pan-domain-auth-play_2-8" % "1.0.4",
          "com.gu" %% "content-api-client" % "15.9",
          ws
      )
  )
