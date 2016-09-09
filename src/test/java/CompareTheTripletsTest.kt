import com.memoizr.assertk.expect
import io.kotlintest.properties.PropertyTesting
import io.kotlintest.properties.TableTesting
import org.junit.Test

class CompareTheTripletsTest : PropertyTesting(), TableTesting {
    @Test
    fun `compares the triplets`() {
        val table = table(
                headers("alice", "bob", "result"),
                row(listOf(0, 0, 0), listOf(1, 1, 1), "0 3"),
                row(listOf(0, 2, 0), listOf(1, 1, 1), "1 2"),
                row(listOf(0, 2, 1), listOf(1, 1, 1), "1 1")
        )

        forAll(table) { a, b, expectedResult ->
            val s = "${a.joinToString(" ")}\n${b.joinToString(" ")}"
            val result: String = CompareTheTriplets().result(s.byteInputStream())
            expect that result isEqualTo expectedResult
        }
    }
}