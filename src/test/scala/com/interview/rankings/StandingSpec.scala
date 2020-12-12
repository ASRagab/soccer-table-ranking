package com.interview.rankings

import com.interview.rankings.Matches._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class StandingSpec extends AnyFlatSpec with should.Matchers {
  import Standing._

  val tabulated = List(
    GameResult(TeamResult("one", Win(3, 2)), TeamResult("two", Loss(1, -2))),
    GameResult(TeamResult("two", Draw(2)), TeamResult("three", Draw(2))),
    GameResult(TeamResult("four", Win(2, 1)), TeamResult("three", Loss(1, -1))),
    GameResult(TeamResult("one", Draw(3)), TeamResult("four", Draw(3)))
  )

  "render" should "correctly render a standing with multiple points" in {
    val standing = Standing(1, "FC Okay", 13)

    render(standing) shouldBe "1. FC Okay, 13 pts"
  }

  it should "correctly render a standing with 1 point" in {
    val standing = Standing(10, "Real Destiny", 1)

    render(standing) shouldBe "10. Real Destiny, 1 pt"
  }

  it should "correctly render a standing with 0 points" in {
    val standing = Standing(11, "Sporting Odessa", 0)

    render(standing) shouldBe "11. Sporting Odessa, 0 pts"
  }

  "getStandings" should "get an ordered list of standings from a list of results and break ties by Name" in {

    val expected = List(Standing(1, "four", 4), Standing(1, "one", 4), Standing(3, "three", 1), Standing(3, "two", 1))

    getStandings(Rankings.breakTiesWithName)(tabulated) shouldBe expected
  }

  "getStandings" should "get an ordered list of standings from a list of results and break ties by goal difference" in {
    val expected = List(Standing(1, "one", 4), Standing(1, "four", 4), Standing(3, "three", 1), Standing(3, "two", 1))

    getStandings(Rankings.breakTiesWithGoalDifference)(tabulated) shouldBe expected
  }

  "getStandings" should "get an ordered list of standings from a list of results and break ties with Dense Rank By Name" in {
    val expected = List(Standing(1, "four", 4), Standing(1, "one", 4), Standing(2, "three", 1), Standing(2, "two", 1))

    getStandings(Rankings.denseRankByName)(tabulated) shouldBe expected
  }

  it should "be okay with an empty list" in {
    val tabulated = List.empty[GameResult]

    val expected = List.empty[Standing]

    getStandings(Rankings.breakTiesWithName)(tabulated) shouldBe expected
  }
}
