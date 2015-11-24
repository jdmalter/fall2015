package secondutilities;

import org.junit.Before;
import org.junit.Test;

public class DequeTest {

	private static final int LIMIT = 100000;

	private Deque<Integer>[] deques;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		deques = (Deque<Integer>[]) new Deque[] { new ArrayDeque<Integer>(),
				new LinkedDeque<Integer>(), new CircularDeque<Integer>() };
	}

	@Test
	public void testSpeed() {
		for (int i = 0; i < deques.length; i++) {
			for (int j = 0; j < LIMIT; j++)
				deques[i].addFirst(j);
			long start = System.nanoTime();
			for (int j = 0; j < LIMIT; j++)
				deques[i].removeLast();
			System.out.println(System.nanoTime() - start);
		}
	}

}