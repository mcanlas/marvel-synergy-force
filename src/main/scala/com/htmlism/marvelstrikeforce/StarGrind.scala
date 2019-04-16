package com.htmlism.marvelstrikeforce

import cats.effect._
import cats.implicits._
import mouse.any._

/**
  * {{{
  *   sbt "runMain com.htmlism.marvelstrikeforce.StarGrind shield,minion city,hero hand guardians kree aim"
  * }}}
  */
object StarGrind extends IOApp {
  private def mainTest(args: List[String]) =
    IO {
      args
        .map(Filter.apply) |> println
    }

  def run(args: List[String]): IO[ExitCode] = {
    val io = new YamlLoader[IO]

    val demo =
      io.campaigns.map(println) *>
        io.supplies.map(println) *>
        io.roster.map(println) *>
        io.bundles.map(println) *>
        io.ranks.map(println) *>
        io.characters.map(println)

    (demo *> mainTest(args))
      .as(ExitCode.Success)
  }
}

object Filter {
  def apply(s: String): Filter =
    s.split(',').toList |> Filter.apply
}

final case class Filter(xs: List[String])
