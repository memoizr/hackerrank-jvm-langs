import java.io.InputStream
import java.util.Scanner

object CircularArrayRotation {
  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val lengthOfArray = scanner.nextInt()
    val numberOfRotations = scanner.nextInt()
    val numberOfSamples = scanner.nextInt()

    val array = Range(0, lengthOfArray) map { _ => scanner.nextInt() }
    val samples = Range(0, numberOfSamples) map { _ => scanner.nextInt() }
    val result = samples
      .map { index => array(((lengthOfArray - (numberOfRotations % lengthOfArray)) + index) % lengthOfArray)}
    result.mkString(start = "", sep = "\n", end = "\n")
  }
}
