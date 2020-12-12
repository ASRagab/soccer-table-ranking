package com.interview.rankings

import cats.implicits.catsSyntaxSemigroup
import com.interview.rankings.Matches.{GameResult, TeamResult}
import com.interview.rankings.Rankings.RankState

case class Standing(rank: Int, team: String, pts: Int)

object Standing {

  def render(standing: Standing): String = {
    val ptsSuffix = if (standing.pts == 1) "pt" else "pts"
    s"${standing.rank}. ${standing.team}, ${standing.pts} $ptsSuffix"
  }

  def getStandings[T](ranker: RankState[T])(tabulated: List[GameResult]): List[Standing] =
    (linearizeResults _ andThen computePoints andThen computeRank(ranker))(tabulated)

  def linearizeResults(tabulated: List[GameResult]): List[TeamResult] =
    tabulated.flatMap(tab => List(tab.home, tab.away))

  def computePoints(results: List[TeamResult]): List[TeamResult] =
    results
      .groupMapReduce(_.name)(_.outcome)(_ |+| _)
      .toList
      .map { case (name, outcome) => TeamResult(name, outcome) }

  def computeRank[T](ranker: RankState[T])(list: List[TeamResult]): List[Standing] =
    list
      .groupBy(_.outcome.pts)
      .toList
      .sortBy(-_._1)
      .map(_._2)
      .foldLeft(ranker) { case (acc, ties) => acc.next(ties) }
      .full
}
