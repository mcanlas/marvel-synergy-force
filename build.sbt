// for cats
scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "mouse" % "0.20"
libraryDependencies += "org.typelevel" %% "cats-effect" % "1.2.0"
libraryDependencies += "io.circe" %% "circe-yaml" % "0.9.0"

scalafmtOnCompile := true
