lazy val root = project in file(".") settings (
                      name :=  "psptemplate",
              organization :=  "org.improving",
                   version :=  "1.0.0-SNAPSHOT",
              scalaVersion :=  "2.11.0",
initialCommands in console :=  "import psp._, psptemplate._",
               fork in run :=  true,
 parallelExecution in Test :=  false,
                  licenses :=  Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
               shellPrompt :=  (s => """%s#%s>""".format(name.value, (Project extract s).currentRef.project)),
               logBuffered :=  false,
  scalacOptions in Compile ++= Seq("-language:*")
)
