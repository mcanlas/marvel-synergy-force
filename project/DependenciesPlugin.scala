import sbt.Keys.*
import sbt.*

object DependenciesPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    implicit class DependencyOps(p: Project) {
      def withCatsEffect: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-effect" % "3.7.0")

      def withTesting: Project =
        p.settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.20" % "test")

      def withYaml: Project =
        p.settings(libraryDependencies += "io.circe" %% "circe-yaml" % "0.15.1")
    }
  }
}
