import java.io.InputStream;
import java.util.Scanner;
import java.util.stream.IntStream;

public class SumOfInputs {
    public static void main(String[] args) {
        System.out.println(result(System.in));
    }
    static int result(InputStream inputStream) {
        Scanner in = new Scanner(inputStream);
        int samples = in.nextInt();
        return IntStream
                .range(0, samples)
                .map(a -> in.nextInt()).reduce((a, b) -> a + b).getAsInt();
    }
}
