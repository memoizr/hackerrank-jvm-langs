import io.kotlintest.properties.Gen
import java.util.*

class IntGenerator(private val limit: Int = 1000) : Gen<Int> {
    override fun generate(): Int {
        return Random().nextInt(limit) + 1
    }
}