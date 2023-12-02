package day2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.DataFrameNaFunctions
object SolutionA extends App {

  val spark = SparkSession.builder().master("local[*]").getOrCreate()

  import spark.implicits._

//  spark.read.text("/Users/jkuperus/dev/playground/advent-of-code-2023/scala/src/main/resources/day2/input_example_a.txt")
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
    .groupBy("game", "turn")
    .pivot("color")
    .max("count")
    .na.fill(0)
    .withColumn("valid", $"red" <= 12 && $"green" <= 13 && $"blue" <= 14)
    .groupBy("game")
    .agg(sum(when($"valid", lit(0)).otherwise(lit(1))).as("num_invalid"))
    .filter($"num_invalid" === 0)
    .select(sum($"game").alias("result"))
    .show(false)
}

