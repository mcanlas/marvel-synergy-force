import sbt.Keys.*
import sbt.*

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    implicit class ProjectOps(p: Project) {
      def withCatsEffect: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-effect" % "3.6.3")

      def withTesting: Project =
        p.settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test")

      def withYaml: Project =
        p.settings(libraryDependencies += "io.circe" %% "circe-yaml" % "0.15.1")

    }
  }
}
