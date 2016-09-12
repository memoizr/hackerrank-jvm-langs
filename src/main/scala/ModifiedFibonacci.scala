import java.io.InputStream
import java.util.Scanner

import scala.annotation.tailrec

object ModifiedFibonacci {
  type Big = java.math.BigDecimal
  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val t1 = scanner.nextInt()
    val t2 = scanner.nextInt()
    val n = scanner.nextInt()
    fibMod(t1,t2, n-1).toString
  }

  def fibMod(t1: Int, t2: Int, n: Int): Big = {
    @tailrec def rec(x: Big, y: Big, m: Int): Big = {
      if (m >= n) x else rec(y, x.add(y.pow(2)), m+1)
    }
    rec(new Big(t1), new Big(t2), 0)
  }

  def fib(n: Int): Int = {
    def fibRec(m: Int): Int = {
      if (m <= 1) 1 else fibRec(m-2) + Math.pow(fibRec(m-1), 2).toInt
    }
    fibRec(n)
  }

  def fibFast(n: Int): Int = {
    @tailrec def rec(x: Int, y: Int, m: Int): Int = {
      if (m >= n) x else rec(y, x + Math.pow(y, 2).toInt, m+1)
    }
    rec(1, 1, 0)
  }
}
