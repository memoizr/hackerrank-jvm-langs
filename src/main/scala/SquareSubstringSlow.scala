import java.io.InputStream
import java.util.Scanner

object SquareSubstringSlow {
  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val numberOfTestCases = scanner.nextLine().toInt

    val testCases = (0 until numberOfTestCases).map { _ => scanner.nextLine() }

    testCases.map { testCase => (0 to testCase.length).map(findPrefixes("", testCase, _)).sum }.mkString("\n")
  }

  private def findPrefixes(prefix: String, remaining: String, k: Int): Int = {
    if (k == 0) if (isASquareString(prefix)) 1 else 0
    else if (remaining.isEmpty) 0
    else findPrefixes(prefix + remaining.head, remaining.tail, k - 1) +
      findPrefixes(prefix, remaining.tail, k)
  }

  private def isASquareString(string: String): Boolean = {
    if (string.length % 2 != 0 || string.isEmpty)
      false
    else {
      val midpoint = string.length / 2
      (0 until midpoint).forall { index =>
        string(index) == string(index + midpoint) }
    }
  }
}
