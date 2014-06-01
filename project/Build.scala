package psp
package build

import sbt._, Keys._

object PspBuild extends sbt.Build {
  private def initial(task: TaskKey[_], command: String) = initialCommands in task ~= (_ + "\n" + command)
  private def initialCommandSettings: SettingSeq = Seq(
    initial(console, "import psp._, psptemplate._"),
    initial(consoleProject, "import psp._, build._")
  )
  def pspStandardSettings: SettingSeq = initialCommandSettings ++ Seq(
                 organization :=  "org.improving",
                      version :=  "1.0.0-SNAPSHOT",
                 scalaVersion :=  "2.11.1",
           crossScalaVersions :=  List("2.10.4", scalaVersion.value),
                  fork in run :=  true,
    parallelExecution in Test :=  false,
                     licenses :=  Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
                  shellPrompt :=  (s => "%s#%s> ".format(name.value, (Project extract s).currentRef.project)),
                  logBuffered :=  false,
        transitiveClassifiers :=  Nil,
                 watchSources ++= buildSourceFiles.value,
                scalacOptions ++= Seq("-language:_"),
                 javacOptions ++= Seq("-nowarn", "-XDignore.symbol.file")
  )
}
