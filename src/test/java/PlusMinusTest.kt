import com.memoizr.assertk.expect
import io.kotlintest.properties.TableTesting
import org.junit.Test
import java.text.DecimalFormat

class PlusMinusTest : TableTesting {
    @Test
    fun `calculates the ration of positive to negative to zero numbers in sequence`() {
        val table = table(
                headers("length", "sequence", "result"),
                row("6", "-4 3 -9 0 4 1", """0.500000
                                             0.333333
                                             0.166667""")
        )

        forAll(table) { length, sequence, result ->
            val inputStream = "$length\n$sequence".byteInputStream()
            val output = PlusMinus.result(inputStream)
            expect that output.map { DecimalFormat("#0.000000").format(it) }.joinToString("\n") isEqualToIgnoringWhitespace result
        }
    }
}