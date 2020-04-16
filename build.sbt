scalaVersion := "2.12.11"

// for cats
scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "mouse"       % "0.24"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.1.2"
libraryDependencies += "io.circe"      %% "circe-yaml"  % "0.12.0"

scalafmtOnCompile := true
