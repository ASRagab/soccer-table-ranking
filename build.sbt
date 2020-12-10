enablePlugins(PackPlugin)

name := "table-ranking"

version := "0.1"

scalaVersion := "2.13.4"

packMain := Map("table-rankings" -> "com.interview.TableRankings")
packResourceDir += (baseDirectory.value / "src/main/resources/" -> "")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "2.3.0",
  "org.typelevel" %% "cats-core"   % "2.3.0",
  "org.scalatest" %% "scalatest"   % "3.2.2" % "test"
)