package psp

import sbt._, Keys._

object Sbtx {
  type SettingOf[A]   = Def.Initialize[A]
  type TaskOf[A]      = Def.Initialize[Task[A]]
  type InputTaskOf[A] = Def.Initialize[InputTask[A]]

  def scalacArgs: Seq[String] = sys.env.getOrElse("SCALAC", "-language:_ -Yno-adapted-args -Ywarn-unused -Ywarn-unused-import").split("\\s+").toSeq
  def junitArgs: Seq[String]  = sys.env.getOrElse("JUNIT", "-a -v -s").split("\\s+").toSeq

  def testSettings = Seq(
      parallelExecution := false,
            logBuffered := false,
             traceLevel := 30,
            testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-verbosity", "1"),
            testOptions += Tests.Argument(TestFrameworks.JUnit, junitArgs: _*)
  )

  def wordSeq(s: String): Vector[String] = scala.Vector(s split "\\s+" filterNot (_ == ""): _*)

  def inBoth[A](f: Configuration => Seq[A]): Seq[A] = List(Test, Compile) flatMap f

  def buildBase                   = baseDirectory in ThisBuild
  def javaSpecVersion: String     = sys.props("java.specification.version")
  def javaCrossTarget(id: String) = buildBase mapValue (_ / "target" / id / s"java_$javaSpecVersion")

  /** Watch out Jonesy! It's the ol' double-cross!
   *  Why, you...
   *
   *  Given a path like src/main/scala we want that to explode into something like the
   *  following, assuming we're currently building with java 1.7 and scala 2.10.
   *
   *    src/main/scala
   *    src/main/scala_2.10
   *    src/main_1.7/scala
   *    src/main_1.7/scala_2.10
   *
   *  Similarly for main/test, 2.10/2.11, 1.7/1.8.
   */
  def doubleCross(config: Configuration) = {
    unmanagedSourceDirectories in config ++= {
      val jappend = Seq("", "_" + javaSpecVersion)
      val sappend = Seq("", "_" + scalaBinaryVersion.value)
      val basis   = (sourceDirectory in config).value
      val parent  = basis.getParentFile
      val name    = basis.getName
      for (j <- jappend ; s <- sappend) yield parent / s"$name$j" / s"scala$s"
    }
  }
  implicit class SettingKeyOps[A](val key: SettingKey[A]) {
    // For a project as obsessed with "map" as is sbt, how did they manage to
    // fuck up calling "map" on a setting? It is implicitly converted to a Task
    // or something, and you don't get a setting back.
    def mapValue[B](f: A => B): SettingOf[B] = Def setting f(key.value)
  }
  implicit class KeyPairOps[A, B](val pair: (SettingKey[A], SettingKey[B])) {
    def mapValue[C](f: (A, B) => C): SettingOf[C] = Def setting f(pair._1.value, pair._2.value)
  }

  implicit class ProjectOps(val p: Project) {
    import p.id

    def root: Project = noArtifacts in file(".")
    def noArtifacts: Project = also(
                publish := (()),
           publishLocal := (()),
         Keys.`package` := file(""),
             packageBin := file(""),
      packagedArtifacts := Map()
    )
    def also(m: ModuleID, ms: ModuleID*): Project     = also(libraryDependencies ++= m +: ms)
    def deps(ms: ModuleID*): Project                  = also(libraryDependencies ++= ms.toSeq)
    def also(s: Setting[_], ss: Setting[_]*): Project = also(s +: ss.toSeq)
    def also(ss: Seq[Setting[_]]): Project            = p settings (ss: _*)

    private def crossed = inBoth(c => Seq(scalacOptions in compile in c ++= scalacArgs)) ++ inBoth(doubleCross)

    def setup(): Project = p also crossed also (
                         name :=  s"psp-$id",
                 organization :=  "org.improving",
                 scalaVersion :=  "2.11.8",
                     licenses :=  Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
                       target <<= javaCrossTarget(id),
      javacOptions in compile ++= Seq("-nowarn", "-XDignore.symbol.file"),
      publishArtifact in Test :=  false,
             triggeredMessage :=  Watched.clearWhenTriggered
    )
  }
}
