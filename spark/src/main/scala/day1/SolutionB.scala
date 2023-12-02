package day1

import org.apache.spark.sql.{Column, SparkSession}
import org.apache.spark.sql.functions._

object SolutionB extends App {
  val spark = SparkSession.builder().master("local[*]").getOrCreate()

  import spark.implicits._

  def to_int(col: Column) =
     when(col === "one", lit(1))
    .when(col === "two", lit(2))
    .when(col === "three", lit(3))
    .when(col === "four", lit(4))
    .when(col === "five", lit(5))
    .when(col === "six", lit(6))
    .when(col === "seven", lit(7))
    .when(col === "eight", lit(8))
    .when(col === "nine", lit(9))
    .otherwise(col).cast("int")

//  spark.read.text("/Users/jkuperus/dev/playground/advent-of-code-2023/scala/src/main/resources/day1/input_example_a.txt")
  spark.read.text("/Users/jkuperus/dev/playground/advent-of-code-2023/scala/src/main/resources/day1/input_a.txt")
    .select($"value", regexp_extract_all($"value", lit("""(?=([0-9]|one|two|three|four|five|six|seven|eight|nine))""")).alias("numbers"))
    .withColumn("first", to_int(element_at($"numbers", 1)))
    .withColumn("last", to_int(element_at($"numbers", -1)))
    .select(sum($"first" * 10 + $"last").as("result"))
    .show(false)
}

