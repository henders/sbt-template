import psp._, Sbtx._, Deps._, ScalacPlugins._

lazy val root = (
  project.root.noArtifacts.crossDirs.useJunit settings (
                                    name :=  "name",
                                 version :=  "0.0.1-SNAPSHOT",
                             description :=  "...",
                            organization :=  "org.improving",
                       scalaOrganization :=  "org.scala-lang",
                            scalaVersion :=  "2.12.2",
                                licenses :=  Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
                Test / parallelExecution :=  false,
       Compile / compile / scalacOptions ++= wordSeq("-language:_ -Yno-adapted-args -Ywarn-unused -Ywarn-unused-import"),
       Compile / console / scalacOptions ++= wordSeq("-language:_ -Yno-adapted-args"),
                  Compile / javacOptions ++= wordSeq("-nowarn -XDignore.symbol.file"),
               console / initialCommands +=  "\nimport java.nio.file._, psp._"
  )
  plugins (
    kindProjector
  )
)
