import psp._, Sbtx._, Deps._

lazy val root = (
  project.root.typelevel.crossDirs.standardOptions.useJunit settings (
                   name := "psp-new",
            description := "...",
           organization := "org.improving",
           scalaVersion := "2.12.1",
     crossScalaVersions := Seq(scalaVersion.value, "2.11.8"),
      parallelExecution := false
  )
  compileDeps (
    scalaz
  )
)
