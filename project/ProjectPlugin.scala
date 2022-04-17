import sbt.Keys._
import sbt._

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    scalaVersion := "2.13.8"
  )

  object autoImport {
    implicit class ProjectOps(p: Project) {
      def withCatsEffect: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.11")

      def withTesting: Project =
        p.settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test")

      def withMouse: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "mouse" % "0.26")

      def withYaml: Project =
        p.settings(libraryDependencies += "io.circe" %% "circe-yaml" % "0.14.1")

    }
  }
}
