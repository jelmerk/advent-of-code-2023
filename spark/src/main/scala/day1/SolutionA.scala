package day1

import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession

object SolutionA extends App {
  val spark = SparkSession.builder().master("local[*]").getOrCreate()

  import spark.implicits._

//    spark.read.text("/Users/jkuperus/dev/playground/advent-of-code-2023/spark/src/main/resources/day1/input_example_a.txt")
    spark.read.text("/Users/jkuperus/dev/playground/advent-of-code-2023/scala/src/main/resources/day1/input_a.txt")
      .select($"value", regexp_extract_all($"value", lit("""([0-9])""")).cast("array<int>").alias("numbers"))
      .withColumn("first", element_at($"numbers", 1))
      .withColumn("last", element_at($"numbers", -1))
      .select(sum($"first" * 10 + $"last").as("result"))
      .show(false)
}
