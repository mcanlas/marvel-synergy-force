package com.htmlism.marvelstrikeforce

import mouse.any._

/**
  * {{{
  *   sbt "runMain com.htmlism.marvelstrikeforce.StarGrind shield,minion city,hero hand guardians kree aim"
  * }}}
  */
object StarGrind {
  YamlLoader.from("nodes.yaml") |> println
  YamlLoader.from("roster.yaml") |> println
  YamlLoader.from("supplies.yaml") |> println
  YamlLoader.from("traits.yaml") |> println

  def main(args: Array[String]): Unit =
    args
      .toList
      .map(Filter.apply) |> println
}

object Filter {
  def apply(s: String): Filter =
    s
      .split(',')
      .toList |> Filter.apply
}

final case class Filter(xs: List[String])