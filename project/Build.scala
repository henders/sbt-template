package psp
package scratch

import sbt._, Keys._
import scala.sys.process.Process

object Build extends sbt.Build {
  def uniqueVersion(v: String) = "%s-%s".format(v, new java.text.SimpleDateFormat("MMdd-HHmm") format new java.util.Date)

  lazy val root = project in file(".") settings (
                        name :=  "psp-scratch",
                organization :=  "org.improving",
                     version :=  uniqueVersion("0.1.0"),
                scalaVersion :=  "2.11.2",
          crossScalaVersions :=  Seq("2.10.4", "2.11.2"),
                 shellPrompt :=  (s => "%s#%s> ".format(name.value, (Project extract s).currentRef.project)),
                 logBuffered :=  false,
                watchSources ++= (baseDirectory.value / "project" * "*.scala").get ++ (baseDirectory.value * "*.sbt").get,
               scalacOptions ++= Seq("-language:_"),
                javacOptions ++= Seq("-nowarn", "-XDignore.symbol.file"),
  initialCommands in console +=  "import java.nio.file._",
                   resolvers +=  "paulp/maven" at "https://dl.bintray.com/paulp/maven",
         libraryDependencies +=  "org.improving" %% "psp-const" % "1.0.0",
                    licenses :=  Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
        cancelable in Global :=  true
  )
}
