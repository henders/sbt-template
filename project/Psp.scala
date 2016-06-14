package psp

import sbt._, Keys._

object Sbtx {
  def junitArgs: Seq[String] = sys.env.getOrElse("JUNIT", "-a -v -s").split("\\s+").toSeq
  def testSettings = Seq(
      parallelExecution := false,
            logBuffered := false,
             traceLevel := 30,
            testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-verbosity", "1"),
            testOptions += Tests.Argument(TestFrameworks.JUnit, junitArgs: _*)
  )
}
