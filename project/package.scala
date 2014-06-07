package psp

import sbt._, Keys._

package object build extends PspTypes with build.StateOps {
  def requireJava8() = sys.props("java.specification.version") match {
    case "1.8" =>
    case v     => throw new Exception("This project requires java 1.8")
  }
  def classTag[A](implicit tag: ClassTag[A]) = tag

  def pathSeparator = java.io.File.pathSeparator
  def separator     = java.io.File.separator

  def buildBase                   = baseDirectory in ThisBuild
  def buildFiles(fs: DirToFiles*) = Def setting (fs map (f => f(buildBase.value).get) flatten)
  def buildSourceFiles            = buildFiles(_ * "*.sbt", _ / "project" * "*.scala")

  private def printResult[A](msg: String)(body: => A): A = {
    val res = body
    println(s"$msg: $res")
    res
  }

  def tablular[A](rows: Seq[A], join: Seq[String] => String)(columns: (A => String)*): String = {
    val cols   = columns.toVector
    val widths = cols map (f => rows map f map (_.length) max)
    def one(width: Int, value: String): String = (
      if (width == 0 || value == "") ""
      else ("%-" + width + "s") format value
    )
    rows map (row => join((widths, cols map (_ apply row)).zipped map one)) mkString "\n"
  }
}
