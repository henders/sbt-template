import psp._, Sbtx._, Deps._, ScalacPlugins._

lazy val root = (
  project.root.noArtifacts.crossDirs.useJunit settings (
                                   name :=  "name",
                                version :=  "0.0.1-SNAPSHOT",
                            description :=  "...",
                           organization :=  "org.improving",
                      scalaOrganization :=  "org.scala-lang",
                           scalaVersion :=  "2.12.2",
              parallelExecution in Test :=  false,
                               licenses :=  Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    scalacOptions in (Compile, compile) ++= wordSeq("-language:_ -Yno-adapted-args -Ywarn-unused -Ywarn-unused-import"),
    scalacOptions in (Compile, console) ++= wordSeq("-language:_ -Yno-adapted-args"),
                javacOptions in Compile ++= wordSeq("-nowarn -XDignore.symbol.file"),
             initialCommands in console +=  "\nimport java.nio.file._, psp._"
  )
  plugins (
    kindProjector
  )
)
