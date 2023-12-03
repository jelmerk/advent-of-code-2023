package day3

import util.geometry.{Area, Coordinates}

import java.nio.file.{Files, Paths}

object SolutionB extends App {

  sealed trait BoardItem {
    val area: Area
  }

  case class PartNumber(value: Long, area: Area) extends BoardItem

  case class Symbol(value: String, area: Area) extends BoardItem


  val input = Files.readString(Paths.get("src/main/resources/day3/input_a.txt"))
//  val input = Files.readString(Paths.get("src/main/resources/day3/input_example_b.txt"))
  val numberedLines = input.split("\n").toList.zipWithIndex

  val partNumbers = numberedLines
    .flatMap { case (line, y) =>
      """[0-9]+""".r.findAllMatchIn(line)
        .map { aMatch =>
          PartNumber(
            value = aMatch.matched.toLong,
            area = Area(
              topLeft = Coordinates(aMatch.start, y),
              bottomRight = Coordinates(aMatch.end - 1, y)
            )
          )
        }
    }

  val symbols = numberedLines
    .flatMap { case (line, y) =>
      """[^0-9.]""".r.findAllMatchIn(line)
        .map { aMatch =>
          Symbol(
            value = aMatch.matched,
            area = Area(
              topLeft = Coordinates(aMatch.start, y),
              bottomRight = Coordinates(aMatch.start, y),
            )
          )
        }
    }

  val result = symbols
    .filter(_.value == "*")
    .map { gear => partNumbers.filter(_.area.isAdjacentTo(gear.area)) }
    .collect { case adjacent if adjacent.size == 2 => adjacent.map(_.value).product }
    .sum

  println(result)

}
