package averages;

import java.util.OptionalDouble;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

class Averager {
//    {
//        System.out.println("instantiating an Averager...");
//    }
    private double sum = 0;
    private long count = 0;

    public Averager() {}

    public static Averager of(double sum, long count) {
        Averager self = new Averager();
        self.sum = sum;
        self.count = count;
        return self;
    }

    public void include(double d) {
        this.count++;
        this.sum += d;
    }

    public void merge(Averager other) {
//        System.out.println("Doing a merge...");
        this.sum += other.sum;
        this.count += other.count;
    }

    public OptionalDouble get() {
        if (count > 0) return OptionalDouble.of(sum/count);
        else return OptionalDouble.empty();
    }
}

public class Average {
    public static void main(String[] args) {
        long start = System.nanoTime();
        DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
//        DoubleStream.iterate(0, x -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
//                .limit(6_000_000_000L)
                .limit(1_000_000_000L)
                .parallel()
//                .unordered()
//                .sequential()
//                .map(x -> Math.sin(x))
                .map(Math::sin)
                .collect(Averager::new, Averager::include, Averager::merge)
//                .collect(() -> new Averager(), (a, i) -> a.include(i), (aFinal, a) -> aFinal.merge(a))
        .get()
        .ifPresent(a -> System.out.println("Mean is " + a));
        long end = System.nanoTime();
        System.out.println("Elapsed time is " + (end - start)/1_000_000_000.0 + " seconds");
    }
}
