package psp
package build

import sbt._
import sbt.complete.Parser

trait PspTypes {
  type ParserOf[A]    = Def.Initialize[State => Parser[A]]
  type SettingOf[A]   = Def.Initialize[A]
  type TaskOf[A]      = Def.Initialize[Task[A]]
  type InputTaskOf[A] = Def.Initialize[InputTask[A]]
  type SettingSeq     = Seq[Setting[_]]
  type ScopedKeySeq   = Seq[Def.ScopedKey[_]]
  type UnscopedKeySeq = Seq[AttributeKey[_]]
  type DirToFiles     = File => PathFinder

  type ClassTag[A] = scala.reflect.ClassTag[A]
  type InputStream = java.io.InputStream
  type jList[A]    = java.util.List[A]
  type jMap[K, V]  = java.util.Map[K, V]
  type jFile       = java.io.File
}
