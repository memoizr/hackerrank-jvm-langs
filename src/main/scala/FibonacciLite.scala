import java.io.InputStream
import java.util
import java.util.Scanner

import scala.collection.JavaConverters._
import java.util.LinkedList

import scala.collection.mutable

object FibonacciLite {
  def result(inputStream: InputStream): Long = {
    val cache = new mutable.HashMap[Int, Long]()
    def fib(n: Int): Long = {
      val maybeLong: Option[Long] = cache.get(n)
      maybeLong.getOrElse(
        if (n <= 1) {
          n.toLong
        } else {
          val result = fib(n - 1) + fib(n - 2)
          cache.put(n, result)
          result
        }
      )
    }
    fib(new Scanner(inputStream).nextInt())
  }
}

object MthToLastElement {
  def mthToLastElement(inputStream: InputStream): Int = {
    val scanner: Scanner = new Scanner(inputStream)
    val index = scanner.nextInt()
    val doubleLinkedList = new util.ArrayList[Int]()
    for (i <- scanner.asScala) {
      doubleLinkedList.add(i.toInt)
    }
    doubleLinkedList.get(doubleLinkedList.size() - index)
  }
}