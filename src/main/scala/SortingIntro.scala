import java.io.InputStream
import java.util.Scanner

object SortingIntro {
  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val numberToFetch = scanner.nextInt()
    val sizeOfArray = scanner.nextInt()
    val list = Range(0, sizeOfArray).map { _ => scanner.nextInt() }
    list.indexOf(numberToFetch).toString
  }
}
