package day1

import java.nio.file.{Files, Paths}

object SolutionA extends App {

  val input = Files.readString(Paths.get("src/main/resources/day1/input_a.txt"))
  val result = input
    .split("\n")
    .map(_.filter(_.isDigit).map(_.asDigit))
    .map(item => item.head * 10 + item.last)
    .sum

  println(result)



}
