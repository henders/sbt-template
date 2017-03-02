import psp._, Sbtx._, Deps._, Plugins._

lazy val root = (
  project.root.typelevel.crossDirs.standardSetup settings (
                          name := "psp-new",
                   description := "...",
                  organization := "org.improving",
                  scalaVersion := "2.12.1",
            crossScalaVersions := Seq(scalaVersion.value, "2.11.8"),
     parallelExecution in Test := false,
                      licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
  )
  deps (
    scalaz
  )
)
