import psp.Sbtx._

                       name := "psp-template"
                description := "description"
               organization := "org.improving"
                   licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
               scalaVersion := "2.11.8"
         crossScalaVersions := Seq(scalaVersion.value, "2.12.0-M4")
 initialCommands in console += "import java.nio.file._, psp._"
                shellPrompt := (s => "%s#%s>".format(name.value, Project extract s currentRef))
                  maxErrors := 15
           triggeredMessage := Watched.clearWhenTriggered
        libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"

inConfig(Test)(testSettings)
