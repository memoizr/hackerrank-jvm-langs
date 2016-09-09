import java.io.InputStream
import java.util.Scanner

object DiagonalDifference {
  def result(inputStream: InputStream) : Int = {
    val scanner = new Scanner(inputStream)
    val n = scanner.nextInt()
    val a = Array.ofDim[Int](n, n)
    for (i <- 0 until n) {
      for (j <- 0 until n) {
        a(i)(j) = scanner.nextInt()
      }
    }
    val primaryDiagonal = Range.apply(0, n).map { i => a(i)(i)}
    val secondaryDiagonal = Range.apply(0, n).map { i => a(i)(n-i-1)}
    println(primaryDiagonal)
    println(secondaryDiagonal)
    Math.abs(secondaryDiagonal.sum - primaryDiagonal.sum)
  }
}