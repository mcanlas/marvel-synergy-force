package com.htmlism.marvelstrikeforce

import cats.effect._
import cats.implicits._
import mouse.any._

object HitList extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    for {
      cs <- CharacterOracle[IO].characters
      _ <- IO { cs |> toTable |> TablePrinter.printTbl }
    } yield ExitCode.Success

  private def toTable(xs: List[Character]) = {
    val cells = ("Name" :: xs.map(_.name).sorted)
      .map(s => Cell(List(s)))

    Table
      .empty
      .addColumn(cells)
  }
}
