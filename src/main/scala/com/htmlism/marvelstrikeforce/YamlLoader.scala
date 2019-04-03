package com.htmlism.marvelstrikeforce

import cats.effect._
import cats.implicits._
import io.circe._
import mouse.any._

trait JsonDecoders {
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

  implicit val shorthandCharacterDecoder: Decoder[ShorthandCharacter] =
    Decoder.forProduct2("Name", "Traits")(ShorthandCharacter.apply)
}

class YamlLoader[F[_]](implicit F: Async[F]) extends JsonDecoders {
  private def unsafeLoadStream(f: String) =
    f |>
      getClass.getClassLoader.getResourceAsStream |>
      (new java.io.InputStreamReader(_))

  private def stream(f: String): F[java.io.Reader] =
    F.delay {
      f |> unsafeLoadStream
    }

  /**
    * Relaxes circe `ParsingFailure` to `Error` to enable `flatMap` chaining.
    */
  private def parse[A](r: java.io.Reader)(implicit dec: Decoder[A]) =
    (r |> yaml.parser.parse : Either[Error, Json]) >>=
      (_.as[A])

  def loadAs[A : Decoder](f: String): F[Error Either A] =
    (f |> stream)
      .map(parse[A])

  def campaigns: F[Either[Error, List[Campaign]]] =
    loadAs[List[Campaign]]("nodes.yaml")

  def supplies: F[Either[Error, Map[String, List[CharacterName]]]] =
    loadAs[String Map List[CharacterName]]("supplies.yaml")

  def roster: F[Either[Error, List[RosterDatum]]] =
    loadAs[List[RosterDatum]]("user.yaml")

  def bundles: F[Either[Error, Map[String, List[Trait]]]] =
    loadAs[String Map List[Trait]]("bundles.yaml")

  def ranks: F[Either[Error, List[Int]]] =
    loadAs[List[Int]]("ranks.yaml")

  def characters: F[Either[Error, List[ShorthandCharacter]]] =
    loadAs[List[ShorthandCharacter]]("characters.yaml")
}

case class Campaign(name: String, filter: Option[String], chapters: List[List[Node]])

case class Node(name: String)

case class CharacterName(s: String)

case class Trait(s: String)

case class RosterDatum(name: CharacterName, rank: Int, shards: Int)

case class ShorthandCharacter(name: String, traits: List[Trait], bundles: List[Trait])
