package com.interview.rankings

import org.scalatest._
import flatspec._
import matchers._

class MatchesSpec extends AnyFlatSpec with should.Matchers {
  import com.interview.rankings.Matches._

  "getMatches" should "correctly assign a winner" in {
    val teamOne = "FC Okay"
    val teamTwo = "Real Barstow"

    val soccerMatch = SoccerMatch(teamOne, 8, teamTwo, 4)

    val expected = GameResult(TeamResult(teamOne, Win(8, 4)), TeamResult(teamTwo, Loss(4, -4)))

    getResult(soccerMatch) shouldBe expected

  }

  it should "correctly assign a draw" in {
    val teamOne = "FC Okay"
    val teamTwo = "Real Barstow"

    val soccerMatch = SoccerMatch(teamOne, 4, teamTwo, 4)

    val expected = GameResult(TeamResult(teamOne, Draw(4)), TeamResult(teamTwo, Draw(4)))

    getResult(soccerMatch) shouldBe expected
  }
}
