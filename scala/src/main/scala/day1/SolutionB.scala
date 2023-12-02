package day1

import java.nio.file.{Files, Paths}

object SolutionB extends App {

  val lookup = Map(
    "0" -> 0,
    "1" -> 1,
    "2" -> 2,
    "3" -> 3,
    "4" -> 4,
    "5" -> 5,
    "6" -> 6,
    "7" -> 7,
    "8" -> 8,
    "9" -> 9,
    "one" -> 1,
    "two" -> 2,
    "three" -> 3,
    "four" -> 4,
    "five" -> 5,
    "six" -> 6,
    "seven" -> 7,
    "eight" -> 8,
    "nine" -> 9,
  )

  val input = Files.readString(Paths.get("src/main/resources/day1/input_a.txt"))

  val result = input
    .split("\n")
    .map(line => "(?=([0-9]|one|two|three|four|five|six|seven|eight|nine))".r.findAllMatchIn(line).map(_.group(1)).toList)
    .map(_.map(lookup.apply))
    .map(item => (item.head * 10) + item.last)
    .sum

  println(result)



}
