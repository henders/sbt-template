package psp
package build

sealed trait Justify
final case object Left extends Justify
final case object Center extends Justify
final case object Right extends Justify
final case class JustifiedString(str: String, width: Int, justify: Justify) {
  def show: String = {
    def pad    = width - str.length
    def spaces = " " * pad
    def left   = " " * (pad / 2)
    def right  = " " * (pad - pad / 2)

    justify match {
      case Left   => str + spaces
      case Right  => spaces + str
      case Center => left + str + right
    }
  }
  override def toString = show
}

final class Table[A](rows: Seq[A])(columns: (A => String)*)(join: Seq[String] => String) {
  def lines = {
    val cols   = columns.toVector
    val widths = cols map (f => rows map f map (_.length) max)
    def one(row: A) = (widths, cols map (_ apply row)).zipped map ((w, str) => new JustifiedString(str, w, Left))
    rows map (row => join(one(row) map (_.show)))
  }
  override lazy val toString = lines mkString "\n"
}
