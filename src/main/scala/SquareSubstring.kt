import java.io.InputStream
import java.util.*

object SquareSubstring {

    @JvmStatic
    fun result(inputStream: InputStream): String {
        val scanner = Scanner(inputStream)
        val numberOfTestCases = Integer.parseInt(scanner.nextLine())

        val testCases = (0 until numberOfTestCases).map { i -> scanner.nextLine() }
        return testCases.map { testCase -> squareSubsequenceCount(testCase) }.joinToString("\n")
    }

    private fun findDoubleSequences(string: String): List<List<Int>> {

        val mapOfPositions = string.indices.groupBy { string[it] }
        return mapOfPositions.values.flatMap { indices ->
            (indices.indices).flatMap { i ->
                (i + 1 until indices.size).map { j -> listOf(indices[i], indices[j]) }
            }
        }
    }

    private fun findCompositeSubsequence(a: List<Int>, b: List<Int>): List<Int> {
        return if (a.contains(b[0]) || a.contains(b[1])) {
            emptyList()
        } else {
            val result = (a + b).sorted()
            if (result.indexOf(b[1]) - result.indexOf(b[0]) == result.size / 2) {
                result
            } else {
                emptyList()
            }
        }
    }

    fun squareSubsequenceCount(input: String): Int {
        val previousRound = findDoubleSequences(input)

        fun recur(substring: Set<List<Int>>, acc: Int): Int {
            return if (substring.isEmpty()) acc
            else {
                val indices: Set<List<Int>> = substring.flatMap { pair ->
                    previousRound.flatMap { otherPair ->
                        val merge = findCompositeSubsequence(pair, otherPair)
                        if (merge.isNotEmpty()) {
                            setOf(merge)
                        } else {
                            emptySet()
                        }
                    }
                }.toSet()
                recur(indices, acc + indices.size)
            }

        }
        return recur(previousRound.toSet(), 0) + previousRound.size
    }
}
