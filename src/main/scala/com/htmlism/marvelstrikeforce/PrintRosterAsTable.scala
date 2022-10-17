package com.htmlism.marvelstrikeforce

import cats.effect._
import cats.syntax.all._

object PrintRosterAsTable extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    new PrintRosterAsTable[IO]
      .program
      .as(ExitCode.Success)

  def toTable(ts: Map[String, List[Trait]])(xs: List[Character]): Table = {
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

    Table
      .empty
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

class PrintRosterAsTable[F[_]: Async] {
  def program: F[Unit] =
    for {
      cs <- CharacterOracle[F].characters
      ts <- new YamlLoader[F].traits
    } yield cs |> PrintRosterAsTable.toTable(ts) |> TablePrinter.printTbl
}
