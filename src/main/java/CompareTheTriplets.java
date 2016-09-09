import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CompareTheTriplets {

    @NotNull
    public String result(
            @NotNull InputStream inputStream) {

        Scanner scanner = new Scanner(inputStream);
        List<Integer> alice = IntStream.range(0, 3).map(x -> scanner.nextInt()).boxed().collect(Collectors.toList());
        List<Integer> bob = IntStream.range(0, 3).map(x -> scanner.nextInt()).boxed().collect(Collectors.toList());
        AtomicInteger aliceResult = new AtomicInteger();
        AtomicInteger bobResult = new AtomicInteger();
        zip(alice.stream(), bob.stream(), Match::new).forEach(it -> {
            if (it.alice > it.bob) {
                aliceResult.incrementAndGet();
            } else if (it.alice < it.bob) {
                bobResult.incrementAndGet();
            }
        });
        String result = aliceResult.get() + " " + bobResult.get();
        return result;
    }

    static class Match {

        final int alice;
        final int bob;

        Match(int alice, int bob) {
            this.alice = alice;
            this.bob = bob;
        }
    }

    public static <A, B, C> Stream<C> zip(Stream<? extends A> a,
            Stream<? extends B> b,
            BiFunction<? super A, ? super B, ? extends C> zipper) {
        Objects.requireNonNull(zipper);
        Spliterator<? extends A> aSpliterator = Objects.requireNonNull(a).spliterator();
        Spliterator<? extends B> bSpliterator = Objects.requireNonNull(b).spliterator();

        // Zipping looses DISTINCT and SORTED characteristics
        int characteristics = aSpliterator.characteristics() & bSpliterator.characteristics() &
                ~(Spliterator.DISTINCT | Spliterator.SORTED);

        long zipSize = ((characteristics & Spliterator.SIZED) != 0)
                ? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown())
                : -1;

        Iterator<A> aIterator = Spliterators.iterator(aSpliterator);
        Iterator<B> bIterator = Spliterators.iterator(bSpliterator);
        Iterator<C> cIterator = new Iterator<C>() {
            @Override
            public boolean hasNext() {
                return aIterator.hasNext() && bIterator.hasNext();
            }

            @Override
            public C next() {
                return zipper.apply(aIterator.next(), bIterator.next());
            }
        };

        Spliterator<C> split = Spliterators.spliterator(cIterator, zipSize, characteristics);
        return (a.isParallel() || b.isParallel())
                ? StreamSupport.stream(split, true)
                : StreamSupport.stream(split, false);
    }
}
