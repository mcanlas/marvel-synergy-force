package com.htmlism.marvelstrikeforce

import io.circe.{Json, ParsingFailure}

import mouse.any._

object YamlLoader {
  def from(f: String): Either[ParsingFailure, Json] =
    f |>
      getClass.getClassLoader.getResourceAsStream |>
      (new java.io.InputStreamReader(_)) |>
      io.circe.yaml.parser.parse
}