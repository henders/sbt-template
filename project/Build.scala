package psp

import sbt._, Keys._

object Deps {
  def cats       = "org.typelevel"  %% "cats-core"       % "0.9.0"
  def config     = "com.typesafe"   %  "config"          % "1.3.1"
  def jawn       = "org.spire-math" %% "jawn-parser"     % "0.10.4"
  def matryoshka = "com.slamdata"   %% "matryoshka-core" % "0.16.10"
  def scalaz     = "org.scalaz"     %% "scalaz-core"     % "7.2.9"
  def shapeless  = "com.chuusai"    %% "shapeless"       % "2.3.2"

  def junit      = "com.novocode"          %  "junit-interface" % "0.11"
  def scalacheck = "org.scalacheck"        %% "scalacheck"      % "1.13.4"
  def scalaprops = "com.github.scalaprops" %% "scalaprops"      % "0.4.1"
}

object Plugins {
  def macroParadise = "org.scalamacros" % "paradise"       % "2.1.0" cross CrossVersion.full
  def kindProjector = "org.spire-math"  % "kind-projector" % "0.9.3" cross CrossVersion.binary
}

object Sbtx {
  import Project.{ inThisBuild, inConfig, inTask, inScope }
  import Def.{ MapScoped, ScopedKey, mapScope }

  type ->[+A, +B]     = (A, B)
  type SettingOf[A]   = Def.Initialize[A]
  type TaskOf[A]      = Def.Initialize[Task[A]]
  type InputTaskOf[A] = Def.Initialize[InputTask[A]]
  type Stg            = Setting[_]
  type Stgs           = Seq[Stg]
  type Strs           = Seq[String]

  def wordSeq(s: String): Strs                = s split "\\s+" filterNot (_ == "") toVector
  def envOr(key: String, value: String): Strs = wordSeq(sys.env.getOrElse(key, value))
  def buildBase                               = baseDirectory in ThisBuild
  def javaSpecVersion: String                 = sys.props("java.specification.version")
  def typelevelArgs                           = wordSeq("-Ypartial-unification -Yliteral-types")

  def withPairs(pairs: (Configuration, TaskKey[_])*)(ss: Stg*): Stgs =
    for ((conf, key) <- pairs; s <- ss) yield s mapKey mapScope(_ in (conf, key.key))

  def withScopes(fs: MapScoped*)(ss: Stg*): Stgs =
    for (f <- fs; s <- ss) yield s mapKey f

  def inScopes(scopes: Scope*)(ss: Stg*): Stgs =
    for (scope <- scopes ; s <- ss) yield s mapKey mapScope(_ => scope)

  def inTasks(keys: TaskKey[_]*)(ss: Stg*): Stgs =
    for (key <- keys ; s <- ss) yield s mapKey mapScope(_ in key.key)

  def inTest(ss: Stg*): Stgs         = inConfig(Test)(ss)
  def inCompile(ss: Stg*): Stgs      = inConfig(Compile)(ss)

  def inCompileTasks(ss: Stg*): Stgs = withPairs(Compile -> compile, Test -> compile)(ss: _*)
  def inConsoleTasks(ss: Stg*): Stgs = withPairs(Compile -> console, Test -> console)(ss: _*)
  def inTestTask(ss: Stg*): Stgs     = withPairs(Test -> test)(ss: _*)

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
  def crossScalaSource(config: Configuration) = Def setting {
    val jappend = Seq("", "_" + javaSpecVersion)
    val sappend = Seq("", "_" + scalaBinaryVersion.value)
    val basis   = (sourceDirectory in config).value
    val parent  = basis.getParentFile
    val name    = basis.getName
    for (j <- jappend ; s <- sappend) yield parent / s"$name$j" / s"scala$s"
  }

  /** This separates bytecode by jvm version, so you can switch
   *  between java versions without it always doing a full recompile.
   */
  def crossJvmTarget(c: Configuration) = Def setting {
    buildBase.value / "target" / thisProject.value.id / s"java_$javaSpecVersion"
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
    def root: Project = noArtifacts in file(".")
    def typelevel     = also(scalaOrganization := "org.typelevel", scalacOptions ++= typelevelArgs)
    def scalalang     = also(scalaOrganization := "org.scala-lang")

    def useJunit = (
      testDeps(Deps.junit)
      also (testOptions in Test += Tests.Argument(TestFrameworks.JUnit, wordSeq("-a -v -s"): _*))
    )
    def useScalacheck = (
      testDeps(Deps.scalacheck)
      also (testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-verbosity", "1"))
    )

    def standardSetup = (
      useJunit
      pluginDeps Plugins.kindProjector
      also inCompileTasks(
        scalacOptions ++= wordSeq("-language:_ -Yno-adapted-args -Ywarn-unused -Ywarn-unused-import"),
         javacOptions ++= wordSeq("-nowarn -XDignore.symbol.file")
      )
      also inConsoleTasks(
          scalacOptions ++= wordSeq("-language:_ -Yno-adapted-args -Ywarn-unused"),
        initialCommands += "\nimport java.nio.file._, psp._"
      )
    )
    def crossDirs: Project = ( this
      also inCompile(
                            target :=  crossJvmTarget(Compile).value,
        unmanagedSourceDirectories ++= crossScalaSource(Compile).value
      )
      also inTest(
                            target :=  crossJvmTarget(Test).value,
        unmanagedSourceDirectories ++= crossScalaSource(Test).value
      )
    )

    def noArtifacts: Project = also(
                publish := (()),
           publishLocal := (()),
         Keys.`package` := file(""),
             packageBin := file(""),
      packagedArtifacts := Map()
    )
    def also(s: Stg, ss: Stg*): Project           = also(s +: ss.toSeq)
    def also(ss: Stgs): Project                   = p settings (ss: _*)

    def depsIn(c: Configuration)(ms: Seq[ModuleID]): Project = also(libraryDependencies ++= ms.map(_ % c))
    def deps(m: ModuleID, ms: ModuleID*): Project            = depsIn(Compile)(m +: ms)
    def testDeps(m: ModuleID, ms: ModuleID*): Project        = depsIn(Test)(m +: ms)
    def pluginDeps(m: ModuleID, ms: ModuleID*): Project      = also(m +: ms flatMap addCompilerPlugin)
  }
}
