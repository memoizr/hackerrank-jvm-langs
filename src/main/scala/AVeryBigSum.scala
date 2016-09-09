import java.io.InputStream
import java.util.Scanner

import scala.collection.immutable.IndexedSeq

class AVeryBigSum {
  def result(inputStream: InputStream): Long = {
    val scanner = new Scanner(inputStream)
    val numberOfInputs = scanner.nextInt()
    val range: Range = Range.apply(0, numberOfInputs)
    range.map { x =>
      scanner.nextInt().toLong
    }.sum
  }
}