package psp
package build

import sbt._, Keys._

class Deps {
  // scala language dependencies
  def scalaCompiler = scalaVersion map ("org.scala-lang" % "scala-compiler" % _)
  def scalaReflect  = scalaVersion map ("org.scala-lang" % "scala-reflect"  % _)
  def scalaParsers  = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1"
  def scalaXml      = "org.scala-lang.modules" %%        "scala-xml"         % "1.0.2"

  // java dependencies
  def ant           = "org.apache.ant"                 %       "ant"       % "1.9.4"
  def asm           = "org.ow2.asm"                    %  "asm-debug-all"  % "5.0.1"
  def diffutils     = "com.googlecode.java-diff-utils" %    "diffutils"    % "1.3.0"
  def jansi         = "org.fusesource.jansi"           %      "jansi"      % "1.11"
  def javaSizeof    = "com.carrotsearch"               %   "java-sizeof"   % "0.0.4"
  def javaxMail     = "javax.mail"                     %      "mail"       % "1.4.4"
  def jline         = "jline"                          %      "jline"      % "2.11"
  def logback       = "ch.qos.logback"                 % "logback-classic" % "1.1.2"
  def nailgunServer = "com.martiansoftware"            % "nailgun-server"  % "0.9.1"

  // scala dependencies
  def improvingJio = "org.improving"  %%    "jio"    % "2.0.0-SNAPSHOT"
  def shapeless    = "com.chuusai"    %% "shapeless" %      "2.0.0"
  def spire        = "org.spire-math" %%   "spire"   %      "0.7.3"

  // test dependencies
  def testInterface  = "org.scala-sbt" % "test-interface"  % "1.0"
  def junitInterface = "com.novocode"  % "junit-interface" % "0.10" % "test"

  def minty      = "org.improving"  %%    "mintiest"     % "1.0.0-SNAPSHOT" % "test"
  def scalacheck = "org.scalacheck" %%   "scalacheck"    %     "1.11.4"     % "test"
  def utest      = "com.lihaoyi"    %%      "utest"      %     "0.1.4"      % "test"

  private def methods       = this.getClass.getMethods.toSeq
  private def moduleMethods = methods filter (m => m.getParameterTypes.isEmpty && m.getReturnType == classOf[ModuleID])
  lazy val modules = moduleMethods map (m => (m invoke this).asInstanceOf[ModuleID])
}

object Deps extends Deps
