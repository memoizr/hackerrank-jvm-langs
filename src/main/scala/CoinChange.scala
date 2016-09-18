import java.io.InputStream
import java.util.Scanner

import scala.collection.mutable

object CoinChange {

  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val money = scanner.nextLong()
    val numberOfDifferentCoins = scanner.nextInt()
    val coins = Range(0, numberOfDifferentCoins).map { _ => scanner.nextLong() }.toList.sorted

    var result = coinChange(money, coins)
    result.toString
  }

  val map = mutable.HashMap.empty[(Long, List[Long]), Long]

  def coinChange(money: Long, coins: List[Long]): Long = {
    if (map.contains((money, coins))) {
      map((money, coins))
    } else {
      val res = if (money == 0) 1
      else if (money < 0 || coins.isEmpty) 0
      else coinChange(money - coins.head, coins) + coinChange(money, coins.tail)
      map((money, coins)) = res
      res
    }
  }
}
