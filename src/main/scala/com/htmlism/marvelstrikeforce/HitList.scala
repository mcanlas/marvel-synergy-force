package com.htmlism.marvelstrikeforce

import cats.effect._
import cats.implicits._

object HitList extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    CharacterOracle[IO]
      .characters
      .map(_.foreach(println))
      .as(ExitCode.Success)
}
