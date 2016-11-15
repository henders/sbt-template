import psp.Sbtx._

                  name := "psp-template"
           description := "description"
          organization := "org.improving"
          scalaVersion := "2.11.8"
     scalaOrganization := "org.scala-lang"
     // scalaOrganization := "org.typelevel"
    crossScalaVersions := Seq(scalaVersion.value, "2.12.0")
              licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
   libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test

           parallelExecution in Global :=  false
                     target in Compile :=  crossJvmTarget(Compile).value
                        target in Test :=  crossJvmTarget(Test).value
 unmanagedSourceDirectories in Compile ++= crossScalaSource(Compile).value
    unmanagedSourceDirectories in Test ++= crossScalaSource(Test).value

inAll(Compile -> compile, Test -> compile)(
  scalacOptions in _ := wordSeq("-language:_ -Yno-adapted-args -Ywarn-unused -Ywarn-unused-import"),
   javacOptions in _ := wordSeq("-nowarn -XDignore.symbol.file")
)
inAll(Compile -> console, Test -> console)(
    scalacOptions in _ := wordSeq("-language:_ -Yno-adapted-args"),
  initialCommands in _ += "import java.nio.file._, psp._"
)
inAll(Test -> packageBin)(
  publishArtifact in _ := false
)
inAll(Test -> test)(
  testOptions in _ += Tests.Argument(TestFrameworks.ScalaCheck, "-verbosity", "1"),
  testOptions in _ += Tests.Argument(TestFrameworks.JUnit, wordSeq("-a -v -s"): _*)
)
