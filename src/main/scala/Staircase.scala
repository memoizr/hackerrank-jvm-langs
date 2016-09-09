import java.io.InputStream
import java.util.Scanner

object Staircase {
  def result(inputStream: InputStream): String = {
    val size = new Scanner(inputStream).nextInt()
    Range.apply(0, size).map { buildString(size, _) }.toList.mkString("\n")
  }

  def buildString(size: Int, index: Int): String = {
    val stringBuilder = new StringBuilder()
    for (i <- 1 until size - index) {
      stringBuilder.append(" ")
    }
    for (i <- 0 to index) {
      stringBuilder.append("#")
    }
    stringBuilder.toString()
  }

}
