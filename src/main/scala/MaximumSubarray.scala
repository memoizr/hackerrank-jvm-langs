import java.io.InputStream
import java.util.Scanner

object MaximumSubarray extends Exercise {
  override def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val numberOfTestCases = scanner.nextInt()
    val result = Range(0, numberOfTestCases).map { _ =>
      val arrayLength = scanner.nextInt()
      val array = Range(0, arrayLength).map { _ => scanner.nextInt() }.toList
      val contiguousMax = calculateMaxSubarraySum(array)
      val totalMax = array.filter {
        _ > 0
      }.reduceOption { (a, b) => a + b }.getOrElse(array.max)
      s"$contiguousMax $totalMax"
    }.mkString("", "\n", "\n")
    println(result)
    result
  }

  def calculateMaxSubarraySum(array: List[Int]): Int = {
    array.tail.foldLeft(Best(array.head, array.head)) { (best, i) =>
      val maxEndingHere = Math.max(i, best.maxEndingHere + i)
      val maxSoFar = Math.max(best.maxSoFar, maxEndingHere)
      Best(maxEndingHere, maxSoFar)
    }.maxSoFar
  }

  case class Best(maxEndingHere: Int, maxSoFar: Int)
}
