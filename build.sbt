import psp._, Sbtx._, Deps._

lazy val proj = (
  project.typelevel.crossDirs settings(
                   name := "psp-proj",
            description := "description",
           organization := "org.improving",
     crossScalaVersions := Seq(scalaVersion.value, "2.11.8"),
               licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
      parallelExecution := false
  )
  also inCompileTasks(
    scalacOptions ++= wordSeq("-language:_ -Yno-adapted-args -Ywarn-unused -Ywarn-unused-import"),
     javacOptions ++= wordSeq("-nowarn -XDignore.symbol.file")
  )
  also inConsoleTasks(
      scalacOptions ++= wordSeq("-language:_ -Yno-adapted-args -Ywarn-unused"),
    initialCommands +=  "import java.nio.file._, psp._"
  )
  also inTestTask(
    testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-verbosity", "1"),
    testOptions += Tests.Argument(TestFrameworks.JUnit, wordSeq("-a -v -s"): _*)
  )
  pluginDeps  (kindProjector)
  compileDeps (scalaz)
  testDeps    (junit)
)
