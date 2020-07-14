scalaVersion := "2.12.12"

// for cats
scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "mouse"       % "0.25"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.1.4"
libraryDependencies += "io.circe"      %% "circe-yaml"  % "0.12.0"

scalafmtOnCompile := true
