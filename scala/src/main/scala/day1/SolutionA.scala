package day1

import java.nio.file.{Files, Paths}

object SolutionA extends App {

  val input = Files.readString(Paths.get("src/main/resources/day1/input_a.txt"))
  val result = input
    .split("\n")
    .map(_.filter(_.isDigit))
    .map(item => Seq(item.head, item.last).mkString.toLong)
    .sum

  println(result)



}
