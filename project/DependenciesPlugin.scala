import sbt.Keys.*
import sbt.*

object DependenciesPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    implicit class DependencyOps(p: Project) {
      def withCatsEffect: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-effect" % Versions.catsEffect)

      def withTesting: Project =
        p.settings(libraryDependencies += "org.scalatest" %% "scalatest" % Versions.scalatest % "test")

      def withYaml: Project =
        p.settings(libraryDependencies += "io.circe" %% "circe-yaml" % Versions.circeYaml)
    }
  }
}
