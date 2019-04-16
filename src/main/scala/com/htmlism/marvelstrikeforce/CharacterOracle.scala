package com.htmlism.marvelstrikeforce

import cats.effect._
import cats.implicits._

object CharacterOracle {
  def apply[F[_]: Async]: CharacterOracle[F] =
    new CharacterOracle[F]

  private def enhanceWith(bundles: Map[String, List[Trait]])(short: ShorthandCharacter): Character = {
    val getExpandedTraits =
      bundles.withDefaultValue(Nil)

    val expandedTraits =
      short.bundles
        .map(_.s) >>= getExpandedTraits

    Character(short.name, short.bundles ::: expandedTraits ::: short.traits)
  }
}

class CharacterOracle[F[_]: Async] {
  def characters: F[List[Character]] = {
    val loader = new YamlLoader[F]

    for {
      shorts  <- loader.characters
      bundles <- loader.bundles
    } yield shorts.map(CharacterOracle.enhanceWith(bundles))
  }
}
