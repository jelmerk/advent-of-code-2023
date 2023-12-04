package day3

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object SolutionA extends App {
  val spark = SparkSession.builder().master("local[*]").getOrCreate()

  import spark.implicits._

    val window = Window.partitionBy("line").orderBy("pos")

    val items = spark.read.option("wholetext", true).text("/Users/jkuperus/dev/playground/advent-of-code-2023/spark/src/main/resources/day3/input_example_a.txt")
//    spark.read.option("wholetext", true).text("/Users/jkuperus/dev/playground/advent-of-code-2023/scala/src/main/resources/day3/input_a.txt")
      .select(posexplode(split($"value", "\n")))
      .select($"pos".alias("line"), posexplode(regexp_extract_all($"col", lit("""([0-9]+|.)"""))))
      .withColumn("start", coalesce(lag(sum(length($"col")).over(window), 1).over(window), lit(0)))
      .withColumn("end", $"start" + length($"col"))
      .filter($"col" =!= ".")
      .drop("pos")

    val partNumbers = items.filter($"col" rlike "[0-9]+")

    val symbols = items.filter(not($"col" rlike "[0-9]+"))

    partNumbers.show(1000, false)
//      .printSchema()
}
