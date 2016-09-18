import java.io.InputStream;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

public class SquareSubstringJavaSlow {

    static String result(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int numberOfTestCases = Integer.parseInt(scanner.nextLine());

        Stream<String> testCases = range(0, numberOfTestCases).boxed().map(i -> scanner.nextLine());

        return testCases
                .map(testCase -> "" + rangeClosed(0, testCase.length())
                        .map(i -> findPrefixes("", testCase, i))
                        .reduce((i, j) -> i + j).orElse(0))
                .reduce((integer, integer2) -> integer + "\n" + integer2).orElse("");
    }

    private static int findPrefixes(String prefix, String remaining, int k) {

        if (k == 0) {
            return isASquareString(prefix) ? 1 : 0;
        }
        if (remaining.isEmpty()) {
            return 0;
        }

        return findPrefixes(prefix + remaining.charAt(0), remaining.substring(1), k - 1) +
                findPrefixes(prefix, remaining.substring(1), k);
    }

    private static boolean isASquareString(String string) {
        if (string.length() % 2 != 0 || string.isEmpty()) {
            return false;
        }

        int i = 0;
        int j = (string.length()) / 2;

        while (i < (string.length()) / 2 && j < string.length()) {
            if (string.charAt(i) != string.charAt(j)) {
                return false;
            }
            i++;
            j++;
        }

        return true;
    }
}
