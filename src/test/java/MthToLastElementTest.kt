import com.memoizr.assertk.expect
import io.kotlintest.properties.TableTesting
import org.junit.Test

class MthToLastElementTest : TableTesting {
    @Test
    fun `Fetches mth to last element`() {
        val table = table(
                headers("index", "list", "result"),
                row(4, listOf(10, 200, 3, 40000, 5), 200),
                row(3, listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), 7)
        )

        forAll(table) { index, list, result ->
            val inputStream = "$index\n${list.joinToString(" ")}".byteInputStream()
            val element = MthToLastElement.mthToLastElement(inputStream)
            expect that element isEqualTo result
        }
    }
}
