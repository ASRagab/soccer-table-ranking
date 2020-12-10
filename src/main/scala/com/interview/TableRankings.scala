package com.interview

import cats.effect.{ExitCode, IO, IOApp, Resource}
import cats.implicits._
import com.interview.rankings.{Matches, Parse, Standing}

import scala.io.BufferedSource

object TableRankings extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- if (args.length < 1)
            IO.raiseError(
              new IllegalArgumentException("Expected 1 argument received 0, filename is probably missing")
            )
          else IO.unit
      exitCode <- Resource
                   .fromAutoCloseable(get(args.head))
                   .use(process)
    } yield exitCode

  def process(resource: BufferedSource): IO[ExitCode] =
    IO {
      resource.getLines
        .map(Parse.parseLine)
        .toList
        .sequence
        .map(_.map(Matches.getResult))
        .map(Standing.getStandings)
        .map(printStandings)
    }.flatMap(_.fold(error => IO(println(error.errorMessage)).as(ExitCode.Error), _ => IO(ExitCode.Success)))

  def get(fileName: String): IO[BufferedSource] =
    IO {
      scala.io.Source.fromResource(fileName)
    }

  def printStandings(list: List[Standing]): Unit =
    list.map(Standing.render).foreach(println)
}
