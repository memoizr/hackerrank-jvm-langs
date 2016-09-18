import java.io.InputStream
import java.util.Scanner

object SquareSubstringFast {
  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val numberOfTestCases = Integer.parseInt(scanner.nextLine())

    val testCases = (0 until numberOfTestCases).map { _ => scanner.nextLine() }
    testCases.map { testCase => squareSubsequenceCount(testCase) }.mkString("\n")
  }

  private def findDoubleSequences(string: String): List[List[Int]] = {
    val mapOfPositions = string.indices.groupBy(string(_))
    mapOfPositions.values.flatMap {
      indices =>
        indices.indices.flatMap {
          i =>
            (i + 1 until indices.size).map {
              j => List(indices(i), indices(j))
            }
        }
    }.toList
  }

  private def findCompositeSubsequence(a: List[Int], b: List[Int]): List[Int] = {
    if (a.contains(b.head) || a.contains(b(1))) {
      List()
    } else {
      val result = (a ++ b).sorted
      if (result.indexOf(b(1)) - result.indexOf(b.head) == result.length / 2) {
        result
      } else {
        List()
      }
    }
  }

  private def squareSubsequenceCount(input: String): Int = {
    val doubleSequences = findDoubleSequences(input)

    def recur(substring: Set[List[Int]], acc: Int): Int = {
      if (substring.isEmpty) acc
      else {
        val indices: Set[List[Int]] = substring.flatMap { pair =>
          doubleSequences.flatMap { otherPair =>
            val merge = findCompositeSubsequence(pair, otherPair)
            if (merge.nonEmpty) {
              Set[List[Int]](merge)
            } else {
              Set[List[Int]]()
            }
          }
        }
        recur(indices, acc + indices.size)
      }
    }
    recur(doubleSequences.toSet, 0) + doubleSequences.size
  }
}
