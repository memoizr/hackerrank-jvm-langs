import com.memoizr.assertk.expect
import io.kotlintest.properties.TableTesting
import org.junit.Test

class DiagonalDifferenceTest : TableTesting {
    @Test
    fun `Calculates the difference of the diagonals`() {
        val table = table(
                headers("matrix", "result"),
                row("""
                    2
                    6 8
                    -6 9
                    """.trimMargin(), "13"
                ),
                row("""
                    4
                    1 2 3 4
                    4 5 6 5
                    1 8 5 6
                    1 8 5 6
                    """.trim(), "2" ),
                row("""
                    3
                    11 2 4
                    4 5 6
                    10 8 -12
                    """.trim(), "15"
                )
        )
        forAll(table) { input, output ->
            expect that DiagonalDifference.result(input.byteInputStream()).toString() isEqualTo output
        }
    }
}