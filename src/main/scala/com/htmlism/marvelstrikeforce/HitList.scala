package com.htmlism.marvelstrikeforce

import cats.effect.*
import cats.syntax.all.*

object HitList extends IOApp:
  def run(args: List[String]): IO[ExitCode] =
    new HitList[IO].program

  def asCounts(xs: List[Character]): Map[Character, Int] =
    xs.map(x => x -> 0).toMap

  def increment(f: Character => Boolean)(xs: Map[Character, Int]): Map[Character, Int] =
    xs.map { case (ch, n) =>
      if ch |> f then ch -> (n + 1)
      else ch            -> n
    }

  def incrementAll(xs: Map[Character, Int]): Map[Character, Int] =
    xs |>
      increment(containsTrait("Cosmic")) |> // Cosmic campaign
      increment(containsTrait("Mystic")) |> // Mystic campaign
      increment(containsTrait("Global")) |> // Global challenge
      increment(containsTrait("Cosmic")) |> // Cosmic challenge
      increment(containsTrait("Hand")) |>   // Relic event
      increment(c => containsTrait("City")(c) && containsTrait("Hero")(c)) |> // Block party event
      increment(c => containsTrait("Kree")(c) && containsTrait("Minion")(c)) |> // Nick fury event
      increment(c => containsTrait("X-Men")(c) || containsTrait("Brotherhood")(c)) |> // Magneto event
      increment(containsTrait("Mercenary")) |>    // Payday event
      increment(containsTrait("S.H.I.E.L.D.")) |> // iron man event
      increment(containsTrait("Spider-verse")) |> // shuri event
      increment(c => containsTrait("Guardian")(c) || containsTrait("Ravager")(c)) |> // star lord
      increment(c => c.name |> Set("Groot", "Rocket Raccoon")) |> // Pairs event
      increment(c => c.name |> Set("Ant-Man", "Wasp")) |> // Pairs event
      increment(c => c.name |> Set("Ms. Marvel", "Scarlet Witch")) // Witch event

  def containsTrait(s: String)(c: Character): Boolean =
    c.traits.contains(Trait(s))

class HitList[F[_]](implicit F: Async[F]):
  def program: F[ExitCode] =
    for {
      cs <- CharacterOracle[F].characters
      _ <- F.delay:
        cs |> HitList.asCounts |> HitList.incrementAll |> (_.toList.sortBy(_._2).reverse) |> (_.foreach(println))
    } yield ExitCode.Success
