import sbt.Keys._
import sbt._

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    scalaVersion := "2.13.3"
  )

  object autoImport {
    implicit class ProjectOps(p: Project) {
      def withCatsEffect: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-effect" % "2.2.0-RC3")

      def withTesting: Project =
        p.settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test")

      def withMouse: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "mouse"       % "0.25")

      def withYaml: Project =
        p.settings(libraryDependencies += "io.circe"      %% "circe-yaml"  % "0.12.0")

    }
  }
}
