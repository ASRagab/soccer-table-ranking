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

    val expected = Result((teamOne, Win), (teamTwo, Loss))

    getResult(soccerMatch) shouldBe expected

  }

  it should "correctly assign a draw" in {
    val teamOne = "FC Okay"
    val teamTwo = "Real Barstow"

    val soccerMatch = SoccerMatch(teamOne, 4, teamTwo, 4)

    val expected = Result((teamOne, Draw), (teamTwo, Draw))

    getResult(soccerMatch) shouldBe expected
  }
}
