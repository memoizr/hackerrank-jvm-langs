
import com.memoizr.assertk.expect
import io.kotlintest.properties.PropertyTesting
import org.junit.Test

class AVeryBigSumTest : PropertyTesting() {
    @Test
    fun foo() {
        forAll(IntGenerator(100)) { a: Int ->
            val list = (0 until a).map { IntGenerator(Math.abs(Int.MAX_VALUE)-1).generate() }
            val string = "$a\n${list.joinToString(" ")}"
            val inputStream = string.byteInputStream()
            val result = AVeryBigSum().result(inputStream)
            expect that result isEqualTo list.map { it.toLong() }.sum()
            list.map { it.toLong() }.sum() == result
        }
    }
}