package com.interview.rankings

import com.interview.rankings.Matches.SoccerMatch
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class ParsersSpec extends AnyFlatSpec with should.Matchers {
  import com.interview.rankings.Parse._

  "parseLine" should "correctly parse a line" in {
    val line = "Real Barstow 10, FC Okay 5"

    val actual = parseLine(line)

    actual.isRight shouldBe true
    actual.map(soccerMatch => soccerMatch shouldBe SoccerMatch("Real Barstow", 10, "FC Okay", 5))
  }

  it should "return a parse error" in {
    val line = "Sporting Odessa3, FC Destiny 3"

    val actual = parseLine(line)

    actual.isLeft shouldBe true
    actual.left.map(error => error shouldBe ParseError(s"`$line` is not a valid match line"))
  }

  it should "extra spaces in name should be okay" in {
    val line = "Sporting Odessa 3, FC   Destiny 3"

    val actual = parseLine(line)

    actual.isRight shouldBe true
  }

  it should "missing score should return parse error" in {
    val line = "Sporting Odessa 3, FC Destiny"

    val actual = parseLine(line)

    actual.isLeft shouldBe true
    actual.left.map(error => error shouldBe ParseError(s"`$line` is not a valid match line"))
  }
}
