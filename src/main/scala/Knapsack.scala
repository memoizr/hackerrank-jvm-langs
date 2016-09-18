import java.io.InputStream
import java.util.Scanner

object Knapsack {
  def result(inputStream: InputStream): String = {
    val scanner = new Scanner(inputStream)
    val numberOfTestCases = scanner.nextInt()

    (0 until numberOfTestCases)
      .map { _ =>
        val lengthOfSamples = scanner.nextInt()
        val target = scanner.nextInt()
        val list = (0 until lengthOfSamples).map(_ => scanner.nextInt()).toList
        knapsack(list, target)
      }
      .mkString("\n")
  }

  def knapsack(lst: List[Int], target: Int): Int = {
    def go(list: List[Int], acc: Int): Int = {
      if (list.isEmpty) target
      else if (acc == 0) acc
      else if (acc < 0) acc + list.head
      else go(list, acc - list.head) min go(list.tail, acc)
    }
    target - go(lst.sorted, target)
  }
}
