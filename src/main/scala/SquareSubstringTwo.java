import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

public class SquareSubstringTwo {

    public static String result(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int numberOfTestCases = Integer.parseInt(scanner.nextLine());

        Stream<String> testCases = range(0, numberOfTestCases).boxed().map(i -> scanner.nextLine());

        return testCases.map(testCase -> "" + sqrSubseqCount(testCase)).reduce((a, b) -> a + "\n" + b).orElse("");
    }

    private static List<List<Integer>> listDoubleSequences(String in) {
        List<List<Integer>> result = new ArrayList<>();

        //map all characters to their indices in the inputstring
        HashMap<Character, List<Integer>> posMap = new HashMap<>();

        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);

            if (posMap.get(c) == null) {
                posMap.put(c, new ArrayList<>());
            }

            posMap.get(c).add(i);
        }


        posMap.values().forEach(indices -> {
            //find all possible permutations with length 2
            for (int i = 0; i < indices.size(); i++) {
                for (int j = i + 1; j < indices.size(); j++) {
                    List<Integer> seq = new ArrayList<>();
                    seq.add(indices.get(i));
                    seq.add(indices.get(j));

                    result.add(seq);
                }
            }
        });

        return result;
    }


    private static List<Integer> merge(List<Integer> a, List<Integer> b) {
        if (a.contains(b.get(0)) || a.contains(b.get(1))) {
            return null;
        }

        List<Integer> result = new ArrayList<>(a);

        result.addAll(b);
        Collections.sort(result);

        if (result.indexOf(b.get(1)) - result.indexOf(b.get(0)) == result.size() / 2) {
            return result;
        } else {
            return null;
        }
    }

    public static int sqrSubseqCount(String in) {
        List<List<Integer>> len_2_seq = listDoubleSequences(in);
        List<List<Integer>> prev_round = new ArrayList<>(len_2_seq);
        final Set<List<Integer>> next_round = new HashSet<>();

        int count = len_2_seq.size();


        while (!prev_round.isEmpty()) {
            next_round.clear();

            prev_round.forEach(l -> len_2_seq.forEach(l2 -> {
                List<Integer> merge = merge(l, l2);

                if (merge != null && !next_round.contains(merge)) {
                    next_round.add(merge);
                }
            }));

            count += next_round.size();

            prev_round.clear();
            prev_round.addAll(next_round);
        }

        return count;
    }
}
