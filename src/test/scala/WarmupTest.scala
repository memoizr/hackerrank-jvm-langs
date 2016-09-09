import java.io.{ByteArrayInputStream, InputStream}
import java.text.DecimalFormat

import org.scalatest.FlatSpec
import org.scalatest.prop.{PropertyChecks, TableDrivenPropertyChecks}
import com.memoizr.assertk.AssertionHooksKt.{getExpect => expect}
import junit.framework.Assert
import org.scalacheck.Gen
import FooExt._

class WarmupTest extends FlatSpec with PropertyChecks with TableDrivenPropertyChecks {
  "Array index" should "be fetched" in {
    val table = Table(
      ("input", "output"),
      (
        """ |4
          |6
          |1 4 5 7 9 12
        """.stripMargin,
        "1")
    )
    forAll(table) { (input, output) =>
      expect that SortingIntro.result(input.byteInputStream) isEqualTo output
    }
  }

  "Even tree" should "calculate max number of cuts to get forest of even trees" in {
    val table = Table(
      ("input", "output"),
      (
        """|10 9
          |2 1
          |3 1
          |4 3
          |5 2
          |6 1
          |7 2
          |8 6
          |9 8
          |10 8""".stripMargin,
        "2"),
      (
        """|20 19
          |2 1
          |3 1
          |4 3
          |5 2
          |6 5
          |7 1
          |8 1
          |9 2
          |10 7
          |11 10
          |12 3
          |13 7
          |14 8
          |15 12
          |16 6
          |17 6
          |18 10
          |19 1
          |20 8""".stripMargin,
        "4")
    )
    forAll(table) { (input, output) =>
      expect that EvenTree.result(input.byteInputStream) isEqualTo output
    }
  }

  "staircase" should "be built" in {
    val table = Table(
      ("size", "output"),
      (6,
        """|     #
          |    ##
          |   ###
          |  ####
          | #####
          |######""".stripMargin)
    )
    forAll(table) { (size: Int, output) =>
      expect that Staircase.result(size.toString.byteInputStream) isEqualTo output
    }
  }

  "TimeConversion" should "conver from 12hrs to 24 hrs" in {
    val table = Table(
      ("input", "output"),
      ("07:05:45PM", "19:05:45"),
      ("12:05:45PM", "12:05:45"),
      ("12:05:45AM", "00:05:45"),
      ("07:05:45AM", "07:05:45")
    )

    forAll(table) { (input, output) =>
      expect that TimeConversion.result(input.byteInputStream) isEqualTo output
    }

    forAll { pm: Boolean =>
      val formatter = new DecimalFormat("#00")
      val hour = Gen.choose(1, 12).sample.get
      val min = Gen.choose(0, 59).sample.get
      val seconds = Gen.choose(0, 59).sample.get
      val input = s"${formatter.format(hour)}:${formatter.format(min)}:${formatter.format(seconds)}${if (pm) "PM" else "AM"}"
      val output = s"${formatter.format(if (pm) (hour % 12) + 12 else hour % 12)}:${formatter.format(min)}:${formatter.format(seconds)}"
      expect that TimeConversion.result(input.byteInputStream) isEqualTo output
      println(s"$input $output")
      Assert.assertTrue(true)
    }
  }

  "Circular array rotation" should "do the proper rotation" in {
    val table = Table(
      ("input", "output"),
      (
        """|3 2 3
          |1 2 3
          |0
          |1
          |2
        """.stripMargin,
        """|2
          |3
          |1
          | """.stripMargin),
      (
        """|3 1 2
          |1 2 3
          |0
          |1
        """.stripMargin,
        """|3
          |1
          | """.stripMargin)
    )

    forAll(table) { (input, output) =>
      expect that CircularArrayRotation.result(input.byteInputStream) isEqualTo output
    }
  }

  "Maximum Subarray" should "compute the maximum contiguous subarray" in {
    val table = Table(
      ("input", "output"),
      (
        """|3
          |6
          |2 -1 2 3 4 -5
          |7
          |4 -5 -4 3 -1 4 -10
          |8
          |4 -5 -4 3 -1 4 -10 10
        """.stripMargin,
        """|10 11
          |6 11
          |10 21
          | """.stripMargin),
      (
        """|6
          |1
          |1
          |6
          |-1 -2 -3 -4 -5 -6
          |2
          |1 -2
          |3
          |1 2 3
          |1
          |-10
          |6
          |1 -1 -1 -1 -1 5
        """.stripMargin,
        """|1 1
          |-1 -1
          |1 1
          |6 6
          |-10 -10
          |5 6
          | """.stripMargin),
      (
        """|2
          |4
          |1 2 3 4
          |6
          |2 -1 2 3 4 -5
        """.stripMargin,
        """|10 10
          |10 11
          | """.stripMargin)
    )

    forAll(table) { (input, output) =>
      expect that MaximumSubarray.result(input.byteInputStream) isEqualTo output
    }
  }
}

object FooExt {

  class ByteInputStreamStingExt(x: String) {
    def byteInputStream = new ByteArrayInputStream(x.getBytes)
  }

  implicit def richString(x: String): ByteInputStreamStingExt = new ByteInputStreamStingExt(x)
}
