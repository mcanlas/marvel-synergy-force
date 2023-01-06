import sbt.Keys._
import sbt._

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    implicit class ProjectOps(p: Project) {
      def withCatsEffect: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-effect" % "3.4.4")

      def withTesting: Project =
        p.settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test")

      def withYaml: Project =
        p.settings(libraryDependencies += "io.circe" %% "circe-yaml" % "0.14.2")

    }
  }
}
