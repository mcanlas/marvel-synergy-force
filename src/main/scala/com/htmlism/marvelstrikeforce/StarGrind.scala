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
  YamlLoader

  private def yamlTest =
    new YamlLoader[cats.effect.IO]
      .doIt
      .map(println)

  private def mainTest(args: List[String]) =
    IO {
      args
        .map(Filter.apply) |> println
    }

  def run(args: List[String]): IO[ExitCode] =
    (yamlTest *> mainTest(args))
      .as(ExitCode.Success)
}

object Filter {
  def apply(s: String): Filter =
    s
      .split(',')
      .toList |> Filter.apply
}

final case class Filter(xs: List[String])