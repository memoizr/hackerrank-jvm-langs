import java.io.InputStream
import java.util.Scanner

object JohnAndTheFences {
  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val numberOfSamples = scanner.nextInt()
    val vector = (0 until numberOfSamples).map(_ => scanner.nextInt()).toSeq
    cutLargestRectangle(vector).toString
  }

  def cutLargestRectangle(vector: Seq[Int]): Int = {
    val x = vector match {
      case Seq() => 0
      case n if n.size == 1 => n.head
      case _ =>
        val i: Int = findMin(vector)._2
        val l = vector.take(i)
        val r = vector.drop(i + 1)
//        println(s"vector: $vector, i: $i, l: $l, r: $r")
//        val idx: Int = i
//        val x = vector(idx)
        cutLargestRectangle(l) max cutLargestRectangle(r)
    }
    println(x)
    x
  }

  def findMin = {
    def go(v: (Option[Int], Int), pos: Int, ss: Seq[Int]): (Option[Int], Int) = {
//      println(s"going, v: $v, pos: $pos, ss: $ss")
      if (pos >= ss.size) v
      else v match {
        case (None, p) => go((Some(ss(pos)), pos), pos + 1, ss)
        case (Some(w), p) => go(if (w < ss(pos)) (Some(w), p)
        else (Some(ss(pos)), pos), pos + 1, ss)
      }
    }
    go((None, 0), 0, _: Seq[Int])
  }

  //  def cutLargestRectangle(vector: Vector[Int]): Int = {
  //    def seachRight(size: Int, vector: Seq[Int]): Int = {
  //      vector.takeWhile(_ >= size).size
  //    }
  //    def searchLeft(size: Int, vector: Seq[Int]): Int = {
  //      for (i <- vector.length - 1 to 0 by -1) {
  //        if (vector(i) < size) return vector.length - 1 - i
  //      }
  //      vector.size
  //    }
  //    val x = vector.indices.map { i =>
  //      val value = vector(i)
  //      value * (1 + searchLeft(value, vector.view(0, i)) + seachRight(value, vector.view(i + 1, vector.length)))
  //    }
  //    x.max
  //  }
}
