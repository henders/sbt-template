package psp

import scala.collection.convert._

package scratch {
  object Main {
    def main(args: Array[String]): Unit = {
      println("Scratch project.")
    }
  }
}

package object scratch extends DecorateAsJava with DecorateAsScala {

}
