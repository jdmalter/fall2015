package utilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacob Malter
 */
public class HeapPriorityQueueTest {

	private static final int SMALL_LIMIT = 100;
	private static final int LARGE_LIMIT = 10000;
	// Stopped 100000000 test after ~1000 seconds
	// Takes ~12 seconds when LARGE_LIMIT = 10000000
	// Takes ~.53 seconds when LARGE_LIMIT = 1000000
	// Takes ~.093 seconds when LARGE_LIMIT = 100000
	// Takes ~.025 seconds when LARGE_LIMIT = 10000

	private PriorityQueue<Integer, Integer> priorityqueue0;
	private PriorityQueue<Integer, Integer> priorityqueue1;

	@Before
	public void setUp() throws Exception {
		testHeapPriorityQueue();
	}

	@Test
	public void testHeapPriorityQueue() {
		priorityqueue0 = new HeapPriorityQueue<Integer, Integer>(null);
		assertEquals(priorityqueue0.size(), 0);
		assertEquals(priorityqueue0.min(), null);
		assertEquals(priorityqueue0.removeMin(), null);
		priorityqueue1 = new HeapPriorityQueue<Integer, Integer>(null);
	}

	@Test
	public void testInsert() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			priorityqueue0.insert(i, 2 * i);
			assertEquals(priorityqueue0.size(), i + 1);
			assertEquals(priorityqueue0.min().getKey().intValue(), 0);
		}
		for (int i = SMALL_LIMIT - 1; i >= 0; i--) {
			priorityqueue1.insert(i, 2 * i);
			assertEquals(priorityqueue1.size(), SMALL_LIMIT - i);
			assertEquals(priorityqueue1.min().getKey().intValue(), i);
		}
	}

	@Test
	public void testRemoveMin() {
		testInsert();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(priorityqueue0.removeMin().getKey().intValue(), i);
		}
	}

	@Test
	public void testLimits() {
		for (int i = 0; i < LARGE_LIMIT; i++) {
			priorityqueue0.insert(i, i);
		}
		for (int i = 0; i < LARGE_LIMIT; i++) {
			priorityqueue0.removeMin();
		}
	}

}
