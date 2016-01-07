package secondutilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PriorityQueueTest {

	private final class TestInteger implements Comparable<TestInteger> {
		private final int integer;

		private TestInteger(int integer) {
			this.integer = integer;
		}

		@Override
		public int compareTo(TestInteger other) {
			if (other == null)
				return 1;
			if (integer > other.integer)
				return 1;
			return integer == other.integer ? 0 : -1;
		}
	}

	private static final int LIMIT = 100;
	private static final int SIZE = 50;

	private PriorityQueue<TestInteger, TestInteger>[] queues;
	private TestInteger[] integers;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		queues = (PriorityQueue<TestInteger, TestInteger>[]) new PriorityQueue[] {
				new ArrayPriorityQueue<TestInteger, TestInteger>(null),
				new UnsortedPriorityQueue<TestInteger, TestInteger>(null),
				new SortedPriorityQueue<TestInteger, TestInteger>(null) };
		integers = new TestInteger[SIZE];
		for (int i = 0; i < SIZE; i++)
			integers[i] = new TestInteger(i);
	}

	@Test
	public void testInsert() {
		for (int i = 0; i < queues.length; i++) {
			assertEquals(queues[i].size(), 0);
			for (int j = 0; j < SIZE / 2; j++) {
				assertEquals(queues[i].size(), j);
				queues[i].insert(integers[j], integers[j]);
				assertEquals(queues[i].size(), j + 1);
			}
			for (int j = SIZE - 1; j >= SIZE / 2; j--) {
				assertEquals(queues[i].size(), (SIZE / 2) + SIZE - j - 1);
				queues[i].insert(integers[j], integers[j]);
				assertEquals(queues[i].size(), (SIZE / 2) + SIZE - j);
			}
		}
	}

	@Test
	public void testPeekKey() {
		testRemove();
	}

	@Test
	public void testPeekValue() {
		testRemove();
	}

	@Test
	public void testRemove() {
		testInsert();
		for (int i = 0; i < queues.length; i++) {
			assertEquals(queues[i].size(), SIZE);
			for (int j = 0; j < SIZE; j++) {
				assertEquals(queues[i].size(), SIZE - j);
				assertEquals(queues[i].peekKey().integer, SIZE - j - 1);
				assertEquals(queues[i].peekValue().integer, SIZE - j - 1);
				assertEquals(queues[i].size(), SIZE - j);
				assertEquals(queues[i].remove().integer, SIZE - j - 1);
				assertEquals(queues[i].size(), SIZE - j - 1);
			}
			assertEquals(queues[i].size(), 0);
			assertEquals(queues[i].peekKey(), null);
			assertEquals(queues[i].peekValue(), null);
			assertEquals(queues[i].remove(), null);
		}
	}

	@Test
	public void testSize() {
		testInsert();
	}

	@Test
	public void testSpeed() {
		TestInteger[] limitIntegers = new TestInteger[LIMIT];
		for (int i = 0; i < LIMIT; i++)
			limitIntegers[i] = new TestInteger(i);
		for (int i = 0; i < queues.length; i++) {
			long start = System.nanoTime();
			for (int j = 0; j < LIMIT; j++)
				queues[i].insert(limitIntegers[j], limitIntegers[j]);
			System.out.println(System.nanoTime() - start);
		}
	}

}