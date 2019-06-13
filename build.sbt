scalaVersion := "2.12.8"

// for cats
scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "mouse"       % "0.21"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.0.0-M4"
libraryDependencies += "io.circe"      %% "circe-yaml"  % "0.9.0"

scalafmtOnCompile := true
