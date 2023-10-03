import sbt.Keys.*
import sbt.*

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    implicit class ProjectOps(p: Project) {
      def withCatsEffect: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.2")

      def withTesting: Project =
        p.settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.16" % "test")

      def withYaml: Project =
        p.settings(libraryDependencies += "io.circe" %% "circe-yaml" % "0.14.2")

    }
  }
}
