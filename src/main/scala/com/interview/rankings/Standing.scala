package com.interview.rankings

import com.interview.rankings.Matches.{Outcome, Result}

case class Standing(rank: Int, team: String, pts: Int)

object Standing {

  def render(standing: Standing): String = {
    val ptsSuffix = if (standing.pts == 1) "pt" else "pts"
    s"${standing.rank}. ${standing.team}, ${standing.pts} $ptsSuffix"
  }

  def getStandings(tabulated: List[Result]): List[Standing] =
    (linearizeResults _ andThen computePoints andThen computeRank)(tabulated)

  def linearizeResults(tabulated: List[Result]): List[(String, Outcome)] =
    tabulated.flatMap(tab => List(tab.teamOne, tab.teamTwo))

  def computePoints(results: List[(String, Outcome)]): List[(String, Int)] =
    results
      .groupMapReduce(_._1)(_._2.pts)(_ + _)
      .toList

  def computeRank(list: List[(String, Int)]): List[Standing] =
    list
      .groupBy(_._2)
      .toList
      .sortBy(-_._1)
      .foldLeft((1, List.empty[Standing])) {
        case ((rank, list), (_, teamPts)) =>
          (rank + teamPts.length, list ++ teamPts.sortBy(_._1).map { case (name, pts) => Standing(rank, name, pts) })
      }
      ._2

}
