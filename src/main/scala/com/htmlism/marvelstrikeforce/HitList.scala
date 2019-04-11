package com.htmlism.marvelstrikeforce

import cats.effect._
import cats.implicits._

object HitList extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    CharacterOracle[IO]
      .characters
      .map(toTable)
      .map(TablePrinter.printTbl)
      .as(ExitCode.Success)

  private def toTable(xs: List[Character]) = {
    val cells = ("Name" :: xs.map(_.name).sorted)
      .map(s => Cell(List(s)))

    Table
      .empty
      .addColumn(cells)
  }
}
