package com.interview.rankings

import cats.Semigroup

object Matches {

  class Outcome(val goals: Int, val goalDifference: Int, val pts: Int)

  final case class Win(override val goals: Int, override val goalDifference: Int)
    extends Outcome(goals, goalDifference, 3)
  final case class Loss(override val goals: Int, override val goalDifference: Int)
    extends Outcome(goals, goalDifference, 0)
  final case class Draw(override val goals: Int, override val goalDifference: Int = 0)
    extends Outcome(goals, goalDifference, 1)

  case class SoccerMatch(teamOne: String, scoreOne: Int, teamTwo: String, scoreTwo: Int)
  case class TeamResult(name: String, outcome: Outcome)

  case class GameResult(home: TeamResult, away: TeamResult)

  def getResult(game: SoccerMatch) =
    game.scoreOne - game.scoreTwo match {
      case tied if tied == 0 =>
        GameResult(TeamResult(game.teamOne, Draw(game.scoreOne)), TeamResult(game.teamTwo, Draw(game.scoreTwo)))
      case first if first > 0 =>
        GameResult(
          TeamResult(game.teamOne, Win(game.scoreOne, first)),
          TeamResult(game.teamTwo, Loss(game.scoreTwo, -first))
        )
      case second if second < 0 =>
        GameResult(
          TeamResult(game.teamOne, Loss(game.scoreOne, -second)),
          TeamResult(game.teamTwo, Win(game.scoreTwo, second))
        )
    }

  implicit def outcomeSemigroup: Semigroup[Outcome] =
    (x: Outcome, y: Outcome) => new Outcome(x.goals + y.goals, x.goalDifference + y.goalDifference, x.pts + y.pts)

}
