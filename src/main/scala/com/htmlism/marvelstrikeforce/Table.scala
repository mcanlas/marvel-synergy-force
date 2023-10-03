package com.htmlism.marvelstrikeforce

object Table:
  val empty: Table = Table(Nil, 0, 0)

case class Table(cells: List[Cell], height: Int, width: Int):
  def cellAt(x: Int, y: Int): Cell =
    cells((y * width) + x)

  def row(n: Int): List[Cell] =
    cells.slice(n * width, n * width + width)

  def addColumn(col: List[Cell]): Table =
    val newWidth = width + 1
    val newHeight =
      if height == 0 then col.length
      else height

    val cellsWithNewColumn =
      (0 until newHeight)
        .toList
        .flatMap(h => row(h) :+ col(h))

    Table(cellsWithNewColumn, newHeight, newWidth)

  def columnWidth(w: Int): Int =
    (0 until height)
      .toList
      .map { h =>
        cellAt(w, h)
      }
      .map(_.maximumWidth)
      .max

  def rowHeight(h: Int): Int =
    (0 until width)
      .toList
      .map { w =>
        cellAt(w, h)
      }
      .map(_.height)
      .max

object Cell:
  val empty: Cell = Cell(Nil)

case class Cell(lines: List[String]):
  def maximumWidth: Int =
    lines
      .map(_.length)
      .max

  def height: Int =
    lines.length

  def line(n: Int): String =
    lines(n)

object TablePrinter:
  def printTbl(tbl: Table): Unit =
    val dashWidth =
      (0 until tbl.width)
        .map(tbl.columnWidth)
        .map(_ + 3) // left pad, right pad, side line
        .sum + 1    // last sideline

    println("-" * dashWidth)

    for h <- 0 until tbl.height do
      for rh <- 0 until tbl.rowHeight(h) do
        for w <- 0 until tbl.width do
          val cell  = tbl.cellAt(w, h)
          val line  = cell.line(rh)
          val width = tbl.columnWidth(w)

          print("|")
          printf(s" %-${width}s ", line)
        println("|")

      println("-" * dashWidth)
