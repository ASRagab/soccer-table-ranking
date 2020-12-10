package com.interview.rankings

import Matches._

object Parse {
  val one      = "one"
  val scoreOne = "scoreOne"
  val two      = "two"
  val scoreTwo = "scoreTwo"

  val gamePattern = "(?<one>.*) (?<scoreOne>\\d+),\\s?(?<two>.*) (?<scoreTwo>\\d+)".r

  def parseLine(line: String): Either[ParseError, SoccerMatch] =
    gamePattern.findFirstMatchIn(line) match {
      case Some(m) => Right(SoccerMatch(m.group(one), m.group(scoreOne).toInt, m.group(two), m.group(scoreTwo).toInt))
      case None    => Left(ParseError(s"`$line` is not a valid match line"))
    }

  case class ParseError(errorMessage: String)
}
