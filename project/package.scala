package psp

import sbt._, Keys._

package object build extends PspTypes {
  def pathSeparator = java.io.File.pathSeparator
  def separator     = java.io.File.separator

  def buildBase                   = baseDirectory in ThisBuild
  def buildFiles(fs: DirToFiles*) = Def setting (fs map (f => f(buildBase.value).get) flatten)
  def buildSourceFiles            = buildFiles(_ * "*.sbt", _ / "project" * "*.scala")
}
