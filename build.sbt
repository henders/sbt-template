import psp._, Sbtx._, Deps._, ScalacPlugins._

lazy val root = (
  project.root.noArtifacts.crossDirs settings (
                          name :=  "name",
                       version :=  "0.0.1-SNAPSHOT",
                   description :=  "...",
                  organization :=  "org.improving",
             scalaOrganization :=  "org.scala-lang",
                  scalaVersion :=  "2.12.1",
     parallelExecution in Test :=  false,
                      licenses :=  Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
      scalacOptions in compile ++= wordSeq("-language:_ -Yno-adapted-args -Ywarn-unused -Ywarn-unused-import"),
       javacOptions in compile ++= wordSeq("-nowarn -XDignore.symbol.file"),
      scalacOptions in console ++= wordSeq("-language:_ -Yno-adapted-args"),
    initialCommands in console +=  "\nimport java.nio.file._, psp._"
  )
  plugins (
    kindProjector
  )
)
