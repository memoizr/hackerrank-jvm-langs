import com.memoizr.assertk.expect
import io.kotlintest.properties.TableTesting
import org.junit.Test

class FibonacciLiteTest : TableTesting {
    @Test
    fun `calculates fibonacci number`() {
        val table = table(
                headers("input", "output"),
                row(12, 144L),
                row(30, 832040L),
                row(30, 832040L),
                row(1, 1L),
                row(0, 0L)
        )

        forAll(table) { input, output ->
            val result = FibonacciLite.result("$input".byteInputStream())
            expect that result isEqualTo output
        }
    }
}