object PrintChapters:
  def main(args: Array[String]): Unit =
    for (c <- 1 to 3)
      for (n <- 1 to 9)
        println(s"      - Name: Mystic $c-$n")
