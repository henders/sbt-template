package psp
package build

import sbt._, Keys._

class JavaDeps {
  // Compile
  def ant            = "org.apache.ant"                 %       "ant"       %    "1.9.4"
  def asm            = "org.ow2.asm"                    %  "asm-debug-all"  %    "5.0.3"
  def caliper        = "com.google.caliper"             %     "caliper"     %  "1.0-beta-1"
  def commonsExec    = "org.apache.commons"             %  "commons-exec"   %     "1.2"
  def commonsHttp    = "org.apache.httpcomponents"      %   "httpclient"    %    "4.3.4"
  def commonsIo      = "org.apache.commons"             %   "commons-io"    %    "1.3.2"
  def diffutils      = "com.googlecode.java-diff-utils" %    "diffutils"    %    "1.3.0"
  def gson           = "com.google.code.gson"           %      "gson"       %    "2.2.4"
  def guava          = "com.google.guava"               %      "guava"      %     "17.0"
  def jansi          = "org.fusesource.jansi"           %      "jansi"      %     "1.11"
  def javaSizeof     = "com.carrotsearch"               %   "java-sizeof"   %    "0.0.4"
  def javaxMail      = "javax.mail"                     %      "mail"       %    "1.4.7"
  def jline          = "jline"                          %      "jline"      %     "2.11"
  def jodaTime       = "joda-time"                      %    "joda-time"    %     "2.3"
  def jmdns          = "javax.jmdns"                    %      "jmdns"      %    "3.4.1"
  def jmdnsAlt       = "com.github.rickyclarkson"       %      "jmdns"      % "3.4.2-r353-1"
  def jna            = "net.java.dev.jna"               %       "jna"       %    "4.1.0"
  def jscience       = "org.jscience"                   %    "jscience"     %    "4.3.1"
  def log4j          = "log4j"                          %      "log4j"      %    "1.2.17"
  def logback        = "ch.qos.logback"                 % "logback-classic" %    "1.1.2"
  def nailgunServer  = "com.martiansoftware"            % "nailgun-server"  %    "0.9.1"
  def servletApi     = "javax.servlet"                  %   "servlet-api"   %     "2.5"      % "provided"
  def slf4jApi       = "org.slf4j"                      %    "slf4j-api"    %    "1.7.7"
  def typesafeConfig = "com.typesafe"                   %     "config"      %    "1.2.1"

  // Test
  def jmock          = "org.jmock"     %      "jmock"      % "2.6.0" % "test"
  def junit          = "junit"         %      "junit"      % "4.11"  % "test"
  def junitInterface = "com.novocode"  % "junit-interface" % "0.10"  % "test"
  def testInterface  = "org.scala-sbt" % "test-interface"  %  "1.0"  % "test"
}

class Deps extends JavaDeps {
  // scala language dependencies
  def scalaCompiler = scalaVersion map ("org.scala-lang" % "scala-compiler" % _)
  def scalaReflect  = scalaVersion map ("org.scala-lang" % "scala-reflect"  % _)
  def scalaParsers  = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1"
  def scalaXml      = "org.scala-lang.modules" %%        "scala-xml"         % "1.0.2"

  // Compile
  def akka          = "com.typesafe.akka"        %%  "akka-actor"   %     "2.3.3"
  def argonaut      = "io.argonaut"              %%   "argonaut"    %     "6.0.4"
  def discipline    = "org.typelevel"            %%  "discipline"   %     "0.2.1"
  def improvingJio  = "org.improving"            %%      "jio"      % "2.0.0-SNAPSHOT"
  def kiama         = "com.googlecode.kiama"     %%     "kiama"     %     "1.6.0"
  def macroParadise = "org.scalamacros"          %%   "paradise"    %     "2.0.0"      % "plugin"
  def parboiled     = "org.parboiled"            %%   "parboiled"   %   "2.0.0-RC2"
  def quasiquotes   = "org.scalamacros"          %%  "quasiquotes"  %     "2.0.0"
  def scalaChart    = "com.github.wookietreiber" %%  "scala-chart"  %     "0.4.2"
  def scalariform   = "com.danieltrinh"          %%  "scalariform"  %     "0.1.5"
  def scalazCore    = "org.scalaz"               %%  "scalaz-core"  %     "7.0.6"
  def scalazStream  = "org.scalaz.stream"        %% "scalaz-stream" %     "0.4.1"
  def scct          = "com.sqality.scct"         %%     "scct"      %      "0.3"
  def scodec        = "org.typelevel"            %%  "scodec-core"  %     "1.0.0"
  def shapeless     = "com.chuusai"              %%   "shapeless"   %     "2.0.0"
  def spire         = "org.spire-math"           %%     "spire"     %     "0.7.5"
  def play          = "com.typesafe.play"        %%     "play"      %     "2.2.2"

  // Test
  def minty          = "org.improving"     %%    "mintiest"     % "1.0.0-SNAPSHOT" % "test"
  def scalacheck     = "org.scalacheck"    %%   "scalacheck"    %     "1.11.4"     % "test"
  def scalameter     = "com.github.axel22" %%   "scalameter"    %     "0.5-M2"     % "test"
  def scalatest      = "org.scalatest"     %%    "scalatest"    %     "2.2.0"      % "test"
  def specs          = "org.specs2"        %%     "specs2"      %     "2.3.12"     % "test"
  def utest          = "com.lihaoyi"       %%      "utest"      %     "0.1.5"      % "test"

  // Homeless
  def thyme = "ichi.bench" % "thyme" % "0.1.1" from "http://plastic-idolatry.com/jars/thyme-0.1.1.jar"

  private def methods       = this.getClass.getMethods.toSeq
  private def moduleMethods = methods filter (m => m.getParameterTypes.isEmpty && m.getReturnType == classOf[ModuleID])
}

object Deps extends Deps {
  val modules: Seq[ModuleID] = moduleMethods sortBy (m => (m.getDeclaringClass.getName, m.getName)) map (m => (m invoke this).asInstanceOf[ModuleID])
  val names: Seq[String]     = moduleMethods.map(_.getName).sorted

  private def moduleGroup(m: ModuleID) = (m.crossVersion == CrossVersion.Disabled, m.configurations == Some("test"))
  private def moduleSort(m: ModuleID)  = (m.name, m.organization)
  private def join(xs: Seq[String])    = xs filterNot (_ == "") mkString " % "

  private def findUpgradesMessage(modules: Seq[ModuleID]): String = {
    val grouped = (modules groupBy moduleGroup mapValues (_ sortBy moduleSort)).toList sortBy (_._1) map (_._2)
    val tables  = grouped map (rows => tablular(rows, join)(_.organization, _.name, _.revision, _.configurations getOrElse ""))
    tables.mkString("Searching for updates for reflectively discovered dependencies, grouped by configurations:\n\n", "\n\n", "\n\n")
  }

  def findUpgrades(s: State): State = {
    val extracted      = Project extract s
    val crossedModules = modules map CrossVersion(extracted get scalaVersion, extracted get scalaBinaryVersion)
    s.log info findUpgradesMessage(crossedModules)
    "dependencyUpdates" :: extracted.append(Seq(libraryDependencies ++= modules), s)
  }
}
