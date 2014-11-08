def prompt(name: String)(state: State) = "%s#%s>".format(name, state.currentRef)

def imports            = "\nimport psp.std._, api._"
def sourcesIn(f: File) = (f * "*.scala").get ++ (f * "*.sbt").get
def repl               = rootResourceDir mapValue (d => IO.read(d / "console.scala"))
def rootResourceDir    = resourceDirectory in Compile in LocalRootProject

               name :=  "plugh"
        description :=  "xyzzy template project"
       organization :=  "xyzzy"
           licenses :=  pspLicenses
            version :=  sbtBuildProps.buildVersion
       scalaVersion :=  scalaVersionLatest
 crossScalaVersions :=  scalaVersionsCross
        shellPrompt :=  prompt(name.value)
        logBuffered :=  false
       watchSources ++= sourcesIn(baseDirectory.value / "project")
       key.initRepl <+= repl
   key.initMetaRepl +=  "import psp.std._, api._"
libraryDependencies +=  "org.improving" %% "psp-std" % "0.5.0"
               test <<= run in Test toTask ""
