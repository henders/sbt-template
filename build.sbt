import psp.Sbtx._

                name := "psp-template"
         description := "description"
        organization := "org.improving"
        scalaVersion := "2.11.8"
   scalaOrganization := "org.scala-lang"
   // scalaOrganization := "org.typelevel"
  crossScalaVersions := Seq(scalaVersion.value, "2.12.0-M5")
            licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
           maxErrors := 15
         shellPrompt := (s => "%s#%s>".format(name.value, Project extract s currentRef))
    triggeredMessage := Watched.clearWhenTriggered
 libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test


inTest(
   parallelExecution := false,
     publishArtifact := false,
         logBuffered := false,
          traceLevel := 30,
         testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-verbosity", "1"),
         testOptions += Tests.Argument(TestFrameworks.JUnit, wordSeq("-a -v -s"): _*)
)

inBoth(c =>
  Seq(
                 scalacOptions in c  :=  wordSeq("-language:_ -Yno-adapted-args -Ywarn-unused -Ywarn-unused-import"),
                  javacOptions in c  :=  wordSeq("-nowarn -XDignore.symbol.file"),
      scalacOptions in console in c  :=  wordSeq("-language:_ -Yno-adapted-args"),
    initialCommands in console in c  +=  "import java.nio.file._, psp._",
                        target in c <<=  crossJvmTarget(c),
    unmanagedSourceDirectories in c <++= crossScalaSource(c)
  )
)
