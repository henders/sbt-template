package psp
package scratch

import sbt._, Keys._

object Build extends sbt.Build {
  def imports = """
    import java.{ lang => jl, util => ju }
    import java.nio.{ file => jnf }
    import ju.{ stream => jus, function => juf }
    import jnf._, attribute._
  """.trim

  lazy val root = project in file(".") settings (
                        name :=  "psp-scratch",
                organization :=  "org.improving",
                     version :=  "0.0.1-SNAPSHOT",
                scalaVersion :=  "2.11.2",
                 shellPrompt :=  (s => "%s#%s> ".format(name.value, (Project extract s).currentRef.project)),
                 logBuffered :=  false,
       transitiveClassifiers :=  Nil,
                watchSources ++= (baseDirectory.value / "project" * "*.scala").get,
                watchSources ++= (baseDirectory.value * "*.sbt").get,
               scalacOptions ++= Seq("-language:_"),
                javacOptions ++= Seq("-nowarn", "-XDignore.symbol.file"),
  initialCommands in console :=  imports
  )
}
