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
          | """.stripMargin,
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
          | """.stripMargin,
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
          | """.stripMargin,
        """|10 10
          |10 11
          | """.stripMargin)
    )

    forAll(table) { (input, output) =>
      expect that MaximumSubarray.result(input.byteInputStream) isEqualTo output
    }
  }

  "Modified fib" should "calculate nth number" in {
    val table = Table(
      ("input", "output"),
      ("0 1 5", "5"),
      ("0 1 10", "84266613096281243382112"))
    forAll(table) { (input, output) =>
      expect that ModifiedFibonacci.result(input.byteInputStream) isEqualTo output
    }
  }

  "Nikita and the Game" should "play the game" in {
    val table = Table(
      ("input", "output"),
      (
        """|3
          |3
          |3 3 3
          |4
          |2 2 2 2
          |7
          |4 1 0 1 1 0 1
          | """.stripMargin,
        """|0
          |2
          |3""".stripMargin),
      (
        """|10
          |42
          |0 2 0 2 0 0 0 0 2 0 0 2 2 0 2 2 2 2 0 0 0 2 0 0 2 2 2 2 2 2 0 0 0 0 2 0 2 0 2 0 2 2
          |24
          |2 0 0 2 2 0 0 0 0 2 0 2 0 2 0 2 0 0 0 2 0 0 2 0
          |64
          |999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994 999999994
          |70
          |1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
          |103
          |4096 4096 1024 256 256 128 128 128 128 512 512 512 512 256 256 512 512 128 32 32 32 32 128 128 1024 1024 2048 512 512 1024 2048 2048 1024 512 512 2048 2048 2048 16384 16384 2048 256 128 128 512 256 256 512 2048 1024 1024 512 512 1024 1024 1024 2048 512 512 1024 4096 4096 4096 2048 2048 4096 2048 2048 2048 1024 512 256 16 16 32 64 128 2048 512 512 512 512 8192 8192 32768 32768 65536 16384 8192 4096 4096 16384 16384 32768 8192 8192 16384 65536 16384 16384 16384 8192 8192
          |77
          |2097152 1048576 1048576 2097152 1048576 262144 262144 524288 1048576 1048576 1048576 1048576 2097152 2097152 1048576 524288 524288 2097152 524288 524288 524288 524288 2097152 524288 262144 262144 524288 131072 131072 131072 131072 262144 65536 65536 32768 32768 65536 262144 262144 1048576 1048576 1048576 262144 262144 524288 524288 131072 65536 65536 32768 32768 65536 32768 32768 65536 2097152 2097152 131072 65536 65536 131072 131072 262144 65536 65536 131072 1048576 2097152 2097152 2097152 4194304 2097152 524288 524288 1048576 4194304 8388608
          |8
          |16384 8192 8192 16384 8192 8192 32768 32768
          |11
          |8760958 8760957 547560 547560 547560 273780 273780 2190239 4380479 4380479 4380478
          |15
          |21211 21211 21211 21211 21211 21211 21211 21211 21211 21211 21211 21211 21211 21211 21211
          |55
          |0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0""".stripMargin,

        """|0
          |0
          |6
          |2
          |15
          |11
          |4
          |1
          |0
          |54""".stripMargin
        )

    )
    forAll(table) { (input, output) =>
      expect that NikitaAndTheGame.result(input.byteInputStream) isEqualTo output
    }
  }

  "Coin change" should "find all ways of making change" in {
    val table = Table(
      ("input", "output"),
      (
        """|4 3
          |1 2 3
        """.stripMargin, "4"),
      (
        """|10 4
          |2 5 3 6
        """.stripMargin, "5"),
      (
        """|250 26
          |8 47 13 24 25 31 32 35 3 19 40 48 1 4 17 38 22 30 33 15 44 46 36 9 20 49""".stripMargin, "3542323427"),

      (
        """|166 23
          |5 37 8 39 33 17 22 32 13 7 10 35 40 2 43 49 46 19 41 1 12 11 28""".stripMargin, "96190959")
    )

    forAll(table) { (input, output) =>
      expect that CoinChange.result(input.byteInputStream) isEqualTo output
    }
  }

  "Red John is back" should "calculate right number" in {
    val table = Table(
      ("input", "output"),
      (
        """ |2
          |1
          |7
        """.stripMargin,
        """|0
          |3""".stripMargin),
      (
        """|20
          |21
          |27
          |23
          |27
          |14
          |25
          |37
          |35
          |17
          |20
          |11
          |11
          |16
          |29
          |34
          |7
          |35
          |38
          |40
          |39""".stripMargin,
        """|91
          |462
          |155
          |462
          |15
          |269
          |8078
          |4522
          |32
          |68
          |8
          |8
          |24
          |816
          |3385
          |3
          |4522
          |10794
          |19385
          |14475""".stripMargin),
      (
        """|
          |20
          |7
          |16
          |14
          |13
          |39
          |13
          |17
          |2
          |16
          |23
          |9
          |32
          |14
          |12
          |17
          |39
          |18
          |21
          |7
          |3""".stripMargin,
        """|3
          |24
          |15
          |11
          |14475
          |11
          |32
          |0
          |24
          |155
          |4
          |1912
          |15
          |9
          |32
          |14475
          |42
          |91
          |3
          |0""".stripMargin)
    )
    forAll(table) { (input, output) =>
      expect that RedJohnIsBack.result(input.byteInputStream) isEqualTo output
    }
  }
  "is prime" should "return primes" in {
    val table = Table(
      ("input", "output"),
      (1, false),
      (2, true),
      (3, true),
      (4, false),
      (5, true),
      (6, false),
      (104729, true),
      (7, true))

    forAll(table) { (input, output) =>
      expect that RedJohnIsBack.isPrime(input) isEqualTo output
    }
  }

  "Square substring" should "Find number of square substrings" in {
    val table = Table(
      ("input", "output"),
      (
        """|3
          |aaa
          |abab
          |baaba""".stripMargin,
        """|3
          |3
          |6""".stripMargin),
      (
        """|20
          |lrbbmqb
          |dar
          |wkkyhid
          |scd
          |jmowfrxsjybldbef
          |rcbyn
          |dyggxxpklorellnmp
          |qfwkho
          |mcoqhnwnk
          |whsqmgbbuqcljjivs
          |dkqtbxixmvtrrbljp
          |snfwzqfj
          |fadrrwsof
          |cnuvqhffbs
          |xwpqcaceh
          |zvfr
          |lnozjkpqpxrjx
          |tzyxacbhh
          |cqcoend
          |mfgdwdwfcgpxiqvku""".stripMargin,
        """|3
          |0
          |1
          |0
          |4
          |0
          |6
          |0
          |1
          |4
          |5
          |1
          |2
          |1
          |1
          |0
          |4
          |1
          |1
          |6""".stripMargin),

      (
        """|20
          |eqlwphapjnadqhdcnvwdtxjbmyppphauxnspusgd
          |iixqmbfjxjcvudjsuyibyebmwsiqyoygyxymzevypzvjegebeocfuftsxdixtigsieehkchzdflilrjqfnxztqrsvbspkyhsenbppkqtpddbuotbbqcwivrfxjujjddntgeiqvdgaijvwcyaubwewpjvygehljxepbpiwuqzdz
          |bdubzvafspqpqwuzifwovyddwyvvburczmgyjgfdxvtnu
          |neslsplwuiupfxlzbknhkwppanltcfirjcddsozoyvegurfwcsfmoxeqmrjowrghwlkobmeahkgccnaehhsveymqpxhlrnunyfdzrhbasjeuygafoubutpnimuwfjq
          |jxvkqdorxxvrwctdsneogvbpkxlpgdirbfcriqifpgynkrrefxsnvucftpwctgtwmxnupycfgcuqunublmoiitncklefszbexrampetvhqnddjeqvuygpnkazqfrpjvoaxdpcwmjobmskskfojnewxgxnnofwltwjwnnvbwjckdmeouuzhy
          |hgvwujbqxxpitcvograiddvhrrdsycqhkleewhxtembaqwqwpqhsuebnvfgvjwdvjjafqzzxlcxdzncqgjlapopkvxfgvicetcmkbljopgtqvvhbgsdvivhesnkqxmwrqidrvmhlubbryktheyentmrobdeyqcrgluaiihveixwjjr
          |opubjguxhxdipfzwswybgfylqvjzharvrlyauuzdrcnjkphclffrkeecbpdipufhidjcxjhrnxcxmjcxohqanxdrmgzebhnlmwpmhwdvthsfque
          |exgrujigskmvrzgfwvrftwapdtutpbztygnsr
          |ajjngcomikjzsdwssznovdruypcnjulkfuzmxnafamespckjcazxdrtdgyrqscczybnvqqcqcjitlvcnvbmasidzgwraatzzwpwmfbfjkncvkelhhzjchpdnlunmppnlgjznkewwuysgefon
          |xpmmsbaopmd
          |zqmkqzxuvtqvnxbslqzkglzamzpdnsjolvybwxxttqognrbaiakqllszkhfzconnmoqklpeefsnsmouwqhodsgcfohesyshmgxwtoayuvnojdjftqtwkbapriujimqwspslgvlcsaqbdbgwtbseet
          |wdnfnbyjvpdjxyuzqxstatbzpctthoofremgfkrbcvkzvgbofthgojhdnaywpnbitoraaibednezwfpdawlohssvtqtkfvsyljzlucqxswyqdntdmfrtzlqsekej
          |zksklfepxchvczysvdgcxbbiswmeaylzifktmoikssfxtgpojxqiysrs
          |fwqdjqnqcgdqrnlluieazvmwnuufnnxvloyvgmliuqandlyavfauaosnlnvacsvpiumoiawcqxswkqwgxyazntnaikameybnuqbcqaggxachrynqxqqmlfotpqhvokiiammqmvxjvbsoaifzyxnjcberrnmix
          |syjhovengbpyqrixqgwdrygxrxkfhicainhwilkqmbpeszdigznzxtzqsjwatycbmjawwmninepfduplucltxmkpvgrrgtuseurageltkcapwpbqromqawixezqkvlfbhwcocpjmrmbpbegvsuluqtuuvkesvjtdhvtjmexfqbvufdpaxc
          |nwqjtbplyzedicwsodpwtqrpyuearhwgfnpaqelofrsotqiktxipqzeqvlqmuoubbjbrpmixfclbstnosvdkujcpwsdqhxrki
          |eziowoqjpiecwxxbjtnmkjgncpmvauqgtausokbfu
          |jtfiuqbjclvlazamucimicnewdoxjlfuemdadgkhufsuevjaxrnivcorhfrqqwnujquoyevslqprlyskrhunljgsoxleuyyfqutozqhmgyetyyepfaesjlkzivdevdllyga
          |xjndjrxhrdyyddqnqd
          |ayshwxshxzjywumbffamxdnxjqoyirmirernekxdlicjfqkkvnxuqmszcixmzkwsqoiwyfalpeuuugfrteomqinuqnirxelpstosaodqszkogrfbxtnpdbltqtmpyye""".stripMargin,
        """|1482
          |496194036
          |1844
          |36203294
          |577692261
          |870385654
          |90100103
          |201
          |27961108
          |6
          |279293957
          |423589169
          |15444
          |552741299
          |277085420
          |11973480
          |184
          |746176656
          |39
          |837155959""".stripMargin)
    )
    forAll(table) { (input, output) =>
      //      expect that SquareSubstringJavaSlow.result(input.byteInputStream) isEqualTo output
      //      expect that SquareSubstringSlow.result(input.byteInputStream) isEqualTo output
      //            expect that SquareSubstring.result(input.byteInputStream) isEqualTo output
      //      expect that SquareSubstringFast.result(input.byteInputStream) isEqualTo output
      expect that SquareSubstringTwo.result(input.byteInputStream) isEqualTo output
    }
  }

  "Knapsack" should "find items" in {
    val table = Table(
      ("input", "output"),
      (
        """|2
          |3 12
          |1 6 9
          |5 9
          |3 4 4 4 8""".stripMargin,
        """|12
          |9""".stripMargin),
      (
        """|1
          |6 11
          |2 3 4 5 8 12""".stripMargin,
        """|11""".stripMargin),
      (
        """|1
          |2 11
          |3 4""".stripMargin,
        """|11""".stripMargin),
      (
        """|1
          |3 17
          |3 5 6""".stripMargin,
        """|17""".stripMargin),
      (
        """|1
          |2 11
          |4 5""".stripMargin,
        """|10""".stripMargin),
      (
        """|1
          |3 11
          |6 7 9""".stripMargin,
        """|9""".stripMargin),
      (
        """|1
          |2 11
          |4 3""".stripMargin,
        """|11""".stripMargin)
    )
    forAll(table) { (input, output) =>
      expect that Knapsack.result(input.byteInputStream) isEqualTo output
    }
  }

  "John and the Fences" should "pick largest rectangle" in {
    val table = Table(
      ("input", "output"),
      (
        """|6
          |2 5 7 4 1 8""".stripMargin,
        "12"),
      (
          scala.io.Source.fromFile("./src/test/scala/JohnAndFence6.txt").mkString,
        "118336")
    )
    forAll(table) { (input, output) =>
      expect that JohnAndTheFences.result(input.byteInputStream) isEqualTo output
    }
  }
}

object FooExt {

  class ByteInputStreamStingExt(x: String) {
    def byteInputStream = new ByteArrayInputStream(x.getBytes)
  }

  implicit def richString(x: String): ByteInputStreamStingExt = new ByteInputStreamStingExt(x)
}
