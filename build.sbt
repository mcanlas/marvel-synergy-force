scalaVersion := "2.12.10"

// for cats
scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "mouse"       % "0.24"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.0.0"
libraryDependencies += "io.circe"      %% "circe-yaml"  % "0.12.0"

scalafmtOnCompile := true
