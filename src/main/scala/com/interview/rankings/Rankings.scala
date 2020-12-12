package com.interview.rankings

import com.interview.rankings.Matches.TeamResult

object Rankings {

  abstract class RankState[T: Ordering](val rank: Int, val tieBreaker: TeamResult => T, val full: List[Standing]) {
    def next(teamResult: List[TeamResult]): RankState[T]
  }

  case class RankStateByName(override val rank: Int, override val full: List[Standing])
    extends RankState[String](rank, _.name, full) {

    override def next(teamResult: List[TeamResult]) =
      RankStateByName(
        rank + teamResult.length,
        this.full ++ teamResult.sortBy(tieBreaker).map(tr => Standing(rank, tr.name, tr.outcome.pts))
      )
  }

  case class DenseRankByName(override val rank: Int, override val full: List[Standing])
    extends RankState[String](rank, _.name, full) {

    override def next(teamResult: List[TeamResult]) =
      DenseRankByName(
        rank + 1,
        this.full ++ teamResult.sortBy(tieBreaker).map(tr => Standing(rank, tr.name, tr.outcome.pts))
      )
  }

  case class RankByGoalDifference(override val rank: Int, override val full: List[Standing])
    extends RankState[Int](rank, -_.outcome.goalDifference, full) {

    override def next(teamResult: List[TeamResult]) =
      RankByGoalDifference(
        rank + teamResult.length,
        this.full ++ teamResult.sortBy(tieBreaker).map(tr => Standing(rank, tr.name, tr.outcome.pts))
      )
  }

  val breakTiesWithName           = RankStateByName(1, List.empty)
  val breakTiesWithGoalDifference = RankByGoalDifference(1, List.empty)
  val denseRankByName             = DenseRankByName(1, List.empty)
}
