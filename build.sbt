import psp._, psp.build._, PspBuild._

lazy val psptemplate = project in file(".") settings (pspStandardSettings: _*)

bintraySettings
