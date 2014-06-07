package psp
package build

import sbt._, Keys._
import scala.language.dynamics

trait StateOps {
  implicit class ConsoleOps[A](val x: A) {
    def > : Unit = println(to_s)
    def to_s: String = x match {
      case xs: Traversable[_] => xs map (_.to_s) mkString "\n"
      case x: ScopedKey[_]    => Def.showFullKey(x)
      case _                  => "" + x
    }
  }

  final class StateCommandDynamics(val s: State) extends Dynamic {
    private def run(command: String): State           = Command.process(command, s)
    def selectDynamic(name: String): State            = run(name)
    def applyDynamic(name: String)(args: Any*): State = run(args mkString (name + " ", " ", ""))
  }

  implicit class ImplicitStateOps(val s: State) {
    def extracted: Extracted            = Project extract s
    def structure: BuildStructure       = extracted.structure
    def session: SessionSettings        = extracted.session
    def currentRef: ProjectRef          = extracted.currentRef // aka session.current
    def currentProject: ResolvedProject = extracted.currentProject
    def currentBase: File               = currentProject.base
    def currentProjectName: String      = currentRef.project

    def originalSettings: SettingSeq                     = session.original sortBy (_.key.key.label)
    def scopedKeys: ScopedKeySeq                         = originalSettings map (_.key)
    def unscopedKeys: UnscopedKeySeq                     = scopedKeys map (_.key)
    def keyLabels: Seq[String]                           = unscopedKeys map (_.label) distinct
    def keysIn(p: Scope => Boolean): ScopedKeySeq        = scopedKeys filter (k => p(k.scope))
    def keysIn(r: Reference): ScopedKeySeq               = keysIn(_.project == Select(r))
    def keysIn(c: Configuration): ScopedKeySeq           = keysIn(_.config == Select(ConfigKey(c.name)))
    def unscopedKeysIn(c: Configuration): UnscopedKeySeq = keysIn(c) map (_.key)

    def printAllSettings(): Unit                         = SessionSettings printAllSettings s
    def withSettings(f: SessionSettings => State): State = SessionSettings.withSettings(s)(f)

    def mapAttributes(f: AttributeMap => AttributeMap): State = s.copy(attributes = f(s.attributes))

    def eval(code: String)        = cmd.eval(code)
    def about                     = cmd.about
    def updates                   = cmd.dependencyUpdates
    def debug(msg: String)        = s.log.debug(msg)
    def info(msg: String)         = s.log.info(msg)
    def warn(msg: String)         = s.log.warn(msg)
    def error(msg: String)        = s.log.error(msg)
    def cmd: StateCommandDynamics = new StateCommandDynamics(s)
  }
}
// sealed trait Settings[Scope] {
//   def data: Map[Scope, AttributeMap]
//   def keys(scope: Scope): Set[AttributeKey[_]]
//   def scopes: Set[Scope]
//   def definingScope(scope: Scope, key: AttributeKey[_]): Option[Scope]
//   def allKeys[T](f: (Scope, AttributeKey[_]) => T): Seq[T]
//   def get[T](scope: Scope, key: AttributeKey[T]): Option[T]
//   def getDirect[T](scope: Scope, key: AttributeKey[T]): Option[T]
//   def set[T](scope: Scope, key: AttributeKey[T], value: T): Settings[Scope]
// }


// sealed trait Command {
//   def help: State => Help
//   def parser: State => Parser[() => State]
//   def tags: AttributeMap
//   def tag[T](key: AttributeKey[T], value: T): Command
// }

// final case class Scope(project: ScopeAxis[Reference], config: ScopeAxis[ConfigKey], task: ScopeAxis[AttributeKey[_]], extra: ScopeAxis[AttributeMap]) {
//   def in(project: Reference, config: ConfigKey): Scope = copy(project = Select(project), config = Select(config))
//   def in(config: ConfigKey, task: AttributeKey[_]): Scope = copy(config = Select(config), task = Select(task))
//   def in(project: Reference, task: AttributeKey[_]): Scope = copy(project = Select(project), task = Select(task))
//   def in(project: Reference, config: ConfigKey, task: AttributeKey[_]): Scope = copy(project = Select(project), config = Select(config), task = Select(task))
//   def in(project: Reference): Scope = copy(project = Select(project))
//   def in(config: ConfigKey): Scope = copy(config = Select(config))
//   def in(task: AttributeKey[_]): Scope = copy(task = Select(task))
// }
