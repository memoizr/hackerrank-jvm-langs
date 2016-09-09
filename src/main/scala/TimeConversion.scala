import java.io.InputStream
import java.text.DecimalFormat
import java.util.Scanner

object TimeConversion {
  def result(inputStream: InputStream): String = {
    val input = new Scanner(inputStream).next()
    val array = input.split(":")
    val hour = array(0)
    val result = if (input.endsWith("PM")) {
      array(0) = new DecimalFormat("#00").format((hour.toInt % 12) + 12)
      array.mkString(":")
    } else {
      array(0) = new DecimalFormat("#00").format(hour.toInt % 12)
      array.mkString(":")
    }
    result.dropRight(2)
  }
}
