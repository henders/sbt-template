                       name :=  "xyzzy"
                description :=  "template project"
               organization :=  "org.improving"
                   licenses :=  Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
                    version :=  "0.0.1-M1"
               scalaVersion :=  "2.11.5"
                shellPrompt :=  prompt(name.value)
                logBuffered :=  false
               watchSources ++= sourcesIn(baseDirectory.value / "project")
 initialCommands in console <+= repl
                       test <<= run in Test toTask ""
        // libraryDependencies +=  "org.scala-lang" % "scala-reflect" % scalaVersion.value

/* */
def prompt(name: String)(state: State) = "%s#%s>".format(name, Project extract state currentRef)

def imports            = "\nimport psp.std._, api._"
def sourcesIn(f: File) = (f * "*.scala").get ++ (f * "*.sbt").get
def repl               = Def setting IO.read(rootResourceDir.value / "console.scala")
def rootResourceDir    = resourceDirectory in Compile in LocalRootProject
