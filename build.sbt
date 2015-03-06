                       name :=  "xyzzy"
                description :=  ""
               organization :=  "org.improving"
                   licenses :=  Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
               scalaVersion :=  "2.11.6"
                shellPrompt :=  prompt(name.value)
                logBuffered :=  false
               watchSources ++= sourcesIn(baseDirectory.value / "project")
 initialCommands in console <+= repl
                       test <<= run in Test toTask ""
                  maxErrors :=  10
           triggeredMessage :=  Watched.clearWhenTriggered
/**
        libraryDependencies ++= Seq(
            "org.scala-lang" % "scala-reflect" % scalaVersion.value
          ,
        )
**/

/* */
def prompt(name: String)(state: State) = "%s#%s>".format(name, Project extract state currentRef)

def sourcesIn(f: File) = (f ** "*.scala").get ++ (f ** "*.sbt").get
def repl               = Def setting IO.read(rootResourceDir.value / "console.scala")
def rootResourceDir    = resourceDirectory in Compile in LocalRootProject
