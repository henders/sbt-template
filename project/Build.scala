package building
package xyzzy

import sbt._, Keys._, psp.libsbt._

object Build extends sbt.Build {
  def prompt(name: String)(state: State) = "%s#%s>".format(name, state.currentRef)
  def imports = "\nimport java.nio.file._, psp.std.api._"

  lazy val root = project in file(".") also standardSettings settings (
                   name :=  "plugh",
            description :=  "xyzzy template project",
           organization :=  "xyzzy",
               licenses :=  pspLicenses,
                version :=  sbtBuildProps.buildVersion,
           scalaVersion :=  scalaVersionLatest,
     crossScalaVersions :=  scalaVersionsCross,
            shellPrompt :=  prompt(name.value),
            logBuffered :=  false,
           watchSources ++= (baseDirectory.value / "project" * "*.scala").get ++ (baseDirectory.value * "*.sbt").get,
           key.initRepl +=  imports + ", xyzzy.plugh._",
       key.initMetaRepl +=  imports,
    libraryDependencies +=  "org.improving" %% "psp-std" % "0.5.0",
                   test :=  (run in Test toTask "").value
  )
}
