import java.io.InputStream
import java.util
import java.util.{Scanner}

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

object PlusMinus {
  def result(inputStream: InputStream): util.List[Double] = {
    val scanner = new Scanner(inputStream)
    val ret: Seq[Double] = for {
      x <- Some(scanner.nextInt()).toSeq
      reducedTuple = scanner.map { numberAsString =>
        numberAsString.toInt
      }.foldLeft((0, 0, 0)) { (tuple, i) =>
        val (positive, negative, zero) = tuple
        i match {
          case y if y > 0 => (positive + 1, negative, zero)
          case y if y < 0 => (positive, negative + 1, zero)
          case _ => (positive, negative, zero + 1)
        }
      }
      listOfFractions <- scala.List(reducedTuple._1, reducedTuple._2, reducedTuple._3).map {
        _.toDouble / x
      }
    } yield listOfFractions
    ret.toList.foreach { println(_) }
    ret.asJava
  }
}