package day2

import java.nio.file.{Files, Paths}
import util.collections.RichCollections._

object SolutionA extends App {


  def turnPossible(turn: List[(String, Long)]): Boolean = {
    val totals = turn.reduceByKey(_ + _)
    totals.getOrElse("red", 0L) <= 12 && totals.getOrElse("green", 0L) <= 13 && totals.getOrElse("blue", 0L) <= 14
  }


  val input = Files.readString(Paths.get("src/main/resources/day2/input_a.txt"))
//  val input = Files.readString(Paths.get("src/main/resources/day2/input_example_a.txt"))

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
    .collect {
      case (game, turns) if turns.forall(turnPossible) => game
    }
    .sum

  println(result)





}
