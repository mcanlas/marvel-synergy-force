object PrintChapters:
  def main(args: Array[String]): Unit =
    for c <- 1 to 3 do for n <- 1 to 9 do println(s"      - Name: Mystic $c-$n")
