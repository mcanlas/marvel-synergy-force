package com.htmlism.marvelstrikeforce

import cats.effect._
import cats.syntax.all._
import io.circe._

trait JsonDecoders {
  implicit val nodeDecoder: Decoder[Node] =
    Decoder.forProduct2("Name", "Shards")(Node.apply)

  implicit val campaignDecoder: Decoder[Campaign] =
    Decoder.forProduct3("Name", "Trait", "Chapters")(Campaign.apply)

  implicit val characterNameDecoder: Decoder[CharacterName] =
    Decoder.decodeString.map(CharacterName.apply)

  implicit val traitDecoder: Decoder[Trait] =
    Decoder.decodeString.map(Trait.apply)

  implicit val rosterDatumDecoder: Decoder[RosterDatum] =
    Decoder.forProduct3("Name", "Rank", "Shards")(RosterDatum.apply)

  implicit val shorthandCharacterDecoder: Decoder[ShorthandCharacter] =
    Decoder.forProduct3("Name", "Traits", "Bundles")(ShorthandCharacter.apply)
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
    *
    * First error happens during string to JSON parsing. Second error happens during JSON to case class parsing.
    */
  private def parse[A](r: java.io.Reader)(implicit dec: Decoder[A]) =
    (r |> yaml.parser.parse: Either[Error, Json]) >>=
      (_.as[A])

  // consider json parsing errors irrecoverable
  private def getOrRaise[A](or: Either[Error, A]) =
    or.fold(F.raiseError[A], F.pure)

  def loadAs[A: Decoder](f: String): F[A] =
    (f |> stream)
      .map(parse[A]) >>= getOrRaise

  def campaigns: F[List[Campaign]] =
    loadAs[List[Campaign]]("nodes.yaml")

  def supplies: F[Map[String, List[CharacterName]]] =
    loadAs[String Map List[CharacterName]]("supplies.yaml")

  def roster: F[List[RosterDatum]] =
    loadAs[List[RosterDatum]]("user.yaml")

  def bundles: F[Map[String, List[Trait]]] =
    loadAs[String Map List[Trait]]("bundles.yaml")

  def ranks: F[List[Int]] =
    loadAs[List[Int]]("ranks.yaml")

  def characters: F[List[ShorthandCharacter]] =
    loadAs[List[ShorthandCharacter]]("characters.yaml")

  def traits: F[String Map List[Trait]] =
    loadAs[String Map List[Trait]]("traits.yaml")
}

case class Campaign(name: String, filter: Option[String], chapters: List[List[Node]])

case class Node(name: String, shards: Option[String])

case class CharacterName(s: String)

case class Trait(s: String)

case class RosterDatum(name: CharacterName, rank: Int, shards: Int)

case class ShorthandCharacter(name: String, traits: List[Trait], bundles: List[Trait])
