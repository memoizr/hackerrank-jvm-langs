import java.io.InputStream
import java.util.Scanner

object RedJohnIsBack {

  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val numberOfTestCases = scanner.nextInt()
    val y = 1.to(3)
    (0 until numberOfTestCases).map { _ => scanner.nextInt() }
      .map {
        caclulateCombinations
      }.map { x =>
      (0 until x + 1).count {
        isPrime
      }
    }.mkString("\n")
  }


  def caclulateCombinations(n: Int): Int = n match {
    case 0 | 1 | 2 | 3 => 1
    case _ => caclulateCombinations(n - 1) + caclulateCombinations(n - 4)
  }

  def isPrime(x: Int): Boolean = {
    if (x < 2) false
    else Y[Int, Boolean] { f => {
      case y@n if y * y > x => true
      case n if x % n == 0 => false
      case d => f(d + 1)
    }
    }(2)
  }

  def Y[A, B](f: (A => B) => (A => B)) = {
    case class W(wf: W => A => B) {
      def apply(w: W) = wf(w)
    }
    val g: W => A => B = w => f(w(w))(_)
    g(W(g))
  }
}
