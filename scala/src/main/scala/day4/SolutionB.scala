package day4

import java.nio.file.{Files, Paths}
import scala.collection.mutable

object SolutionB extends App {

  def parseNumbers(input: String) = input.trim.split("""\s+""").map(_.toInt).toSet

  val Line = """^Card\s+([0-9]+):(.*)\|(.*)""".r

  val input = Files.readString(Paths.get("src/main/resources/day4/input_a.txt"))
//    val input = Files.readString(Paths.get("src/main/resources/day4/input_example_a.txt"))

  val cardValues: Map[Int, Int] = input.split("\n")
    .map { case Line(card, winning, mine) =>
      val winningNumbers = parseNumbers(winning)
      val myNumbers = parseNumbers(mine)

      val myWinningNumbers = myNumbers.intersect(winningNumbers)
      (card.toInt, myWinningNumbers.size)
    }
    .toMap

  val cardNumbers = cardValues.keys.toList.sorted
  val largestCard = cardNumbers.last

  val queue = mutable.Queue(cardNumbers : _*)

  var counter = 0
  while(!queue.isEmpty) {
    val card = queue.dequeue()

    counter += 1
    val numCopiesWon = cardValues(card)

    val cardsWon = (1 to numCopiesWon).map(_ + card).filterNot(_ > largestCard)

    cardsWon.foreach(card => queue.enqueue(card))
  }

  println(counter)

}
