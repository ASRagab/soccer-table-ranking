package com.interview.rankings

object Matches {
  sealed abstract class Outcome(val pts: Int)
  case object Win  extends Outcome(3)
  case object Loss extends Outcome(0)
  case object Draw extends Outcome(1)

  case class SoccerMatch(teamOne: String, scoreOne: Int, teamTwo: String, scoreTwo: Int)
  case class Result(teamOne: (String, Outcome), teamTwo: (String, Outcome))

  def getResult(game: SoccerMatch) =
    game.scoreOne - game.scoreTwo match {
      case 0                    => Result((game.teamOne, Draw), (game.teamTwo, Draw))
      case first if first > 0   => Result((game.teamOne, Win), (game.teamTwo, Loss))
      case second if second < 0 => Result((game.teamOne, Loss), (game.teamTwo, Win))
    }
}
