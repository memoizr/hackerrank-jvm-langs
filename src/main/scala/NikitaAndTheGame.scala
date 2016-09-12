import java.io.InputStream
import java.util.Scanner

object NikitaAndTheGame {
  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val tescases = scanner.nextInt()
    Range(0, tescases.toInt).map { _ =>
      val numberOfSamples = scanner.nextInt()
      Range(0, numberOfSamples).map { _ => scanner.nextLong() }.scan(0L) {
        _ + _
      }.tail.toVector
    }.map(findMaxPossibleScoreForGame).mkString("\n")
  }

  def findMaxPossibleScoreForGame(vector: Vector[Long]): Int = {
    def rec(offset: Long, vec: Vector[Long]): Int = {
      val midPoint = (vec.last - offset) / 2
      val i = vec.lastIndexOf(midPoint + offset) + 1
      if (i == 0 || vec.length < 2 || vec.last % 2 != 0) {
        0
      } else {
        val leftSide = vec.take(i)
        val rightSide = vec.drop(i)
        val leftSearch = rec(offset, leftSide)
        val rightSearch = rec(midPoint + offset, rightSide)
        (leftSearch max rightSearch) + 1
      }
    }

    if (vector.head == vector.last) {
      vector.length - 1
    } else rec(0, vector)
  }
}
