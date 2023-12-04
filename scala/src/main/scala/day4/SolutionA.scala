package day4

import java.nio.file.{Files, Paths}

object SolutionA extends App {

  def parseNumbers(input: String) = input.trim.split("""\s+""").map(_.toInt).toSet

  val Line = """^Card\s+([0-9]+):(.*)\|(.*)""".r

  val input = Files.readString(Paths.get("src/main/resources/day4/input_a.txt"))
//  val input = Files.readString(Paths.get("src/main/resources/day4/input_example_a.txt"))

  val result = input.split("\n")
    .map { case Line(card, winning, mine) =>
      val winningNumbers = parseNumbers(winning)
      val myNumbers = parseNumbers(mine)

      val myWinningNumbers = myNumbers.intersect(winningNumbers)
      (card.toInt, myWinningNumbers)
    }
    .collect {
      case (_, numbers) if numbers.nonEmpty =>
        math.pow(2, numbers.size - 1).toInt
    }
    .sum

  println(result)
}
