package ohm.softa.a12;

import java.util.Iterator;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Examples {
	public static Stream<Integer> createStream() {
		return Stream.generate(new PseudoRandomNumberSupplier());
	}

	private static class PseudoRandomNumberSupplier implements Supplier<Integer> {
		private final Random random = new Random();

		@Override
		public Integer get() {
			return random.nextInt();
		}
	}
	public static Stream<Integer> createStream(int n) {
		Spliterator<Integer> it = Spliterators.spliteratorUnknownSize(new RandomIterator(n), Spliterator.DISTINCT);
		return StreamSupport.stream(it, false);
	}

	private static class RandomIterator implements Iterator<Integer> {
		private int n;
		private final Random random = new Random();

		public RandomIterator(int n) {
			this.n = n;
		}

		@Override
		public boolean hasNext() {
			return n > 0;
		}

		@Override
		public Integer next() {
			n--;
			return random.nextInt();
		}
	}

	public static void main(String... args) {
		Stream<Integer> prngStream1 = createStream(5);
		System.out.println(prngStream1.count());

		Stream<Integer> prngStream2 = createStream();
		prngStream2.limit(5).forEach(System.out::println);
	}
}

