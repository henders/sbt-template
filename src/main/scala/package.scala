package psp

package object psptemplate {
  implicit final class AnyExtensions[A](val lhs: A) extends AnyVal {
    def toRef: AnyRef = lhs.asInstanceOf[AnyRef]
  }
}
