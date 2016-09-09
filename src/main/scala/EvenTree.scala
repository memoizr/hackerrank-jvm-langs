import java.io.InputStream
import java.util.Scanner

import scala.collection.immutable.Stream.Empty
import scala.collection.mutable

object EvenTree {
  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val numberOfNodes = scanner.nextInt()
    val numberOfEdges = scanner.next()

    val tree = new mutable.HashMap[Int, Node]()
    tree.put(1, Node(None))
    List.range(0, numberOfNodes - 1)
      .map { _ => (scanner.nextInt(), scanner.nextInt()) }
      .foreach { pair =>
        val (child, parent) = pair
        val cachedParent = tree.get(parent).get
        val cachedChild = tree.getOrElseUpdate(child, {
          Node(Some(cachedParent))
        })
        cachedParent.addChild()
      }
//    println(tree)
   val x = for {
      (node, actualNode) <- tree if actualNode.childCount > 0 && node != 1
    } yield { actualNode.childCount }
    x.filter { _ % 2 != 0}.size.toString
  }

  case class Node(parent: Option[Node]) {
    var childCount = 0

    def addChild(): Unit = {
      parent.foreach {
        _.addChild()
      }
      childCount += 1
    }

    override def toString: String = s"${childCount}"
  }
}

