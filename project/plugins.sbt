addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.8")

addSbtPlugin("com.gu" % "sbt-riffraff-artifact" % "1.1.17")

libraryDependencies += "org.vafer" % "jdeb" % "1.8" artifacts Artifact("jdeb", "jar", "jar")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.10.0-RC1")

