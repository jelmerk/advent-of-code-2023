package day2

import java.nio.file.{Files, Paths}
import util.collections.RichCollections._

object SolutionB extends App {

  val input = Files.readString(Paths.get("src/main/resources/day2/input_a.txt"))
//    val input = Files.readString(Paths.get("src/main/resources/day2/input_example_a.txt"))

  val Line = """Game ([0-9]+):\s(.*)""".r
  val result = input
    .split("\n")
    .map {
      case Line(game, record) =>
        val turns = record.split("; ").toList

        game.toLong -> turns.map { turn =>
          val pulls = turn.split(", ").toList

          pulls.map { pull =>
            val Array(count, color) = pull.split(" ")
            color -> count.toLong
          }

        }
    }
    .map {
      case (game, turns) =>
        turns
          .flatMap { _.reduceByKey(_ + _) }
          .reduceByKey { case (a, b) => math.max(a, b)}
          .values
          .product
    }
    .sum

  println(result)


}
