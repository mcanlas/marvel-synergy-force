package com.htmlism.marvelstrikeforce

import cats.effect._
import cats.implicits._
import mouse.any._

object HitList extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    for {
      cs <- CharacterOracle[IO].characters
      ts <- new YamlLoader[IO].traits
      _  <- IO { cs |> toTable(ts) |> TablePrinter.printTbl }
    } yield ExitCode.Success

  private def toTable(ts: Map[String, List[Trait]])(xs: List[Character]) = {
    val sortedCharacters =
      xs.sortBy(_.name)

    val names = ("Name" :: sortedCharacters.map(_.name))
      .map(s => Cell(List(s)))

    val allegiance =
      narrowTraits("Allegiance", sortedCharacters, ts)

    val roles =
      narrowTraits("Role", sortedCharacters, ts)

    val origins =
      narrowTraits("Origin", sortedCharacters, ts)

    val jurisdiction =
      narrowTraits("Jurisdiction", sortedCharacters, ts)

    val teams =
      narrowTraits("Team", sortedCharacters, ts)

    Table.empty
      .addColumn(names)
      .addColumn(allegiance)
      .addColumn(jurisdiction)
      .addColumn(origins)
      .addColumn(roles)
      .addColumn(teams)
  }

  def narrowTraits(s: String, characters: List[Character], traits: String Map List[Trait]): List[Cell] = {
    val collide = traits(s)

    val traitsForCharacters =
      characters
        .map { c =>
          c.traits.filter(collide.toSet).map(_.s).mkString(", ")
        }

    val strings = s :: traitsForCharacters

    strings
      .map(s => Cell(List(s)))
  }
}
