package utilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacob Malter
 */
public class LinkedDequeTest {

	private static final int SMALL_LIMIT = 10;
	private static final int LARGE_LIMIT = 10000;
	// Takes ~317 seconds when LARGE_LIMIT = 100000000
	// Takes ~9.2 seconds when LARGE_LIMIT = 10000000
	// Takes ~.1 seconds when LARGE_LIMIT = 1000000
	// Takes ~.015 seconds when LARGE_LIMIT = 100000
	// Takes ~.005 seconds when LARGE_LIMIT = 10000

	private Deque<Integer> deque0;

	@Before
	public void setUp() throws Exception {
		testLinkedDeque();
	}

	@Test
	public void testLinkedDeque() {
		deque0 = new LinkedDeque<Integer>();
		assertEquals(deque0.size(), 0);
		assertEquals(deque0.first(), null);
		assertEquals(deque0.last(), null);
	}

	@Test
	public void testRemoveFirst() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			deque0.addLast(i);
		}
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(deque0.removeFirst().intValue(), i);
		}
		assertEquals(deque0.removeFirst(), null);
	}

	@Test
	public void testRemoveLast() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			deque0.addLast(i);
		}
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(deque0.removeLast().intValue(), SMALL_LIMIT - i - 1);
		}
		assertEquals(deque0.removeLast(), null);
	}

	@Test
	public void testFirst() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			deque0.addFirst(i);
			assertEquals(deque0.first().intValue(), i);
		}
	}

	@Test
	public void testLast() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			deque0.addLast(i);
			assertEquals(deque0.last().intValue(), i);
		}
	}

	@Test
	public void testLimits() {
		for (int i = 0; i < LARGE_LIMIT; i++) {
			deque0.addLast(i);
		}
		for (int i = 0; i < LARGE_LIMIT; i++) {
			deque0.removeFirst();
		}
	}

}
