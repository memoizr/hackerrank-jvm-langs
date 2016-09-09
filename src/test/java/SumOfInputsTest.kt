
import io.kotlintest.properties.PropertyTesting
import org.junit.Test

class SumOfInputsTest: PropertyTesting() {
    @Test
    fun `sums the inputs`() {
        forAll(IntGenerator()) { a: Int ->
            val list = (0 until a).map { IntGenerator().generate() }
            val s = "$a\n${list.joinToString(" ")}"
            list.sum() == SumOfInputs.result(s.byteInputStream())
        }
    }

}
