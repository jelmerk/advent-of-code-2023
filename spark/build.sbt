name := "advent-of-code-2023"

version := "1.0"

scalaVersion := "2.12.14"

scalacOptions := Seq(
  "-encoding",
  "UTF-8",
)

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-sql" % "3.5.0"
)
