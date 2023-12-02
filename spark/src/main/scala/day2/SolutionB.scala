package day2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object SolutionB extends App {

  val spark = SparkSession.builder().master("local[*]").getOrCreate()

  import spark.implicits._

  spark.read.text("/Users/jkuperus/dev/playground/advent-of-code-2023/scala/src/main/resources/day2/input_a.txt")
    .select(
      regexp_extract($"value", """^Game ([0-9]+):\s(.*)""", 1).cast("int").as("game"),
      posexplode(split(regexp_extract($"value", """Game ([0-9]+):\s(.*)""", 2), "; "))
    )
    .select(
      $"game",
      $"pos".alias("turn"),
      explode(split($"col", ", ")).as("pull")
    )
    .select(
      $"game",
      $"turn",
      split($"pull", " ").getItem(0).cast("int").as("count"),
      split($"pull", " ").getItem(1).as("color")
    )
    .groupBy("game")
    .pivot("color")
    .max("count")
    .select(($"red" * $"blue" * $"green").alias("game_total"))
    .select(sum("game_total").as("total"))
    .show(false)
}
