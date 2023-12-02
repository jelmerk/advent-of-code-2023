package day1

import java.nio.file.{Files, Paths}

object SolutionB extends App {

  val words = Seq("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    .zipWithIndex
    .toMap

  val numbers = (0 to 9).map(_.toString).zipWithIndex.toMap

  val lookup = words ++ numbers

  val input = Files.readString(Paths.get("src/main/resources/day1/input_a.txt"))

  val result = input
    .split("\n")
    .map(line => "(?=([0-9]|one|two|three|four|five|six|seven|eight|nine))".r.findAllMatchIn(line)
      .map(_.group(1))
      .map(lookup.apply)
      .toList
    )
    .map(item => (item.head * 10) + item.last)
    .sum

  println(result)



}
