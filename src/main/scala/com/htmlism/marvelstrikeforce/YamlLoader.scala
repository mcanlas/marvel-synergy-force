package com.htmlism.marvelstrikeforce

import cats.implicits._
import io.circe._
import mouse.any._

object YamlLoader {
  /**
    * Relaxes circe `ParsingFailure` to `Error` to enable `flatMap` chaining.
    */
  def from(f: String): Either[Error, Json] =
    f |>
      getClass.getClassLoader.getResourceAsStream |>
      (new java.io.InputStreamReader(_)) |>
      io.circe.yaml.parser.parse

  def fromAs[A](f: String)(implicit decoder: Decoder[A]): Either[Error, A] =
    (f |> from) >>= (_.as[A])

  implicit val nodeDecoder: Decoder[Node] =
    Decoder.forProduct1("Name")(Node.apply)

  implicit val campaignDecoder: Decoder[Campaign] =
    Decoder.forProduct3("Name", "Trait", "Chapters")(Campaign.apply)

  implicit val characterNameDecoder: Decoder[CharacterName] =
    Decoder.decodeString.map(CharacterName.apply)

  implicit val traitDecoder: Decoder[Trait] =
    Decoder.decodeString.map(Trait.apply)

  implicit val rosterDatumDecoder: Decoder[RosterDatum] =
    Decoder.forProduct3("Name", "Rank", "Shards")(RosterDatum.apply)

  YamlLoader
    .fromAs[List[Campaign]]("nodes.yaml") |> println
  YamlLoader.from("roster.yaml") |> println
  YamlLoader.fromAs[String Map List[CharacterName]]("supplies.yaml") |> println
  YamlLoader
    .fromAs[String Map List[Trait]]("traits.yaml") |> println

  YamlLoader
    .fromAs[List[Int]]("ranks.yaml") |> println

  YamlLoader
    .fromAs[List[RosterDatum]]("user.yaml") |> println
}

case class Campaign(name: String, filter: Option[String], chapters: List[List[Node]])

case class Node(name: String)

case class CharacterName(s: String)

case class Trait(s: String)

case class RosterDatum(name: CharacterName, rank: Int, shards: Int)