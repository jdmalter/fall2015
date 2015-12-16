package utilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacob Malter
 */
public class LinkedPositionalListTest {

	private static final int SMALL_LIMIT = 10;
	private static final int LARGE_LIMIT = 10000;
	// Takes ~315 seconds when LARGE_LIMIT = 100000000
	// Takes ~11 seconds when LARGE_LIMIT = 10000000
	// Takes ~.13 seconds when LARGE_LIMIT = 1000000
	// Takes ~.015 seconds when LARGE_LIMIT = 100000
	// Takes ~.005 seconds when LARGE_LIMIT = 10000

	private PositionalList<Integer> list0;

	@Before
	public void setUp() throws Exception {
		testLinkedPositionalList();
	}

	@Test
	public void testLinkedPositionalList() {
		list0 = new LinkedPositionalList<Integer>();
		assertEquals(list0.size(), 0);
		assertEquals(list0.first(), null);
		assertEquals(list0.last(), null);
	}

	@Test
	public void testBefore() {
		testAddFirst();
		Position<Integer> position = list0.last();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(position.getData().intValue(), i);
			position = list0.before(position);
		}
		assertEquals(position, null);
	}

	@Test
	public void testAfter() {
		testAddLast();
		Position<Integer> position = list0.first();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(position.getData().intValue(), i);
			position = list0.after(position);
		}
		assertEquals(position, null);
	}

	@Test
	public void testAddFirst() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			list0.addFirst(i);
			assertEquals(list0.size(), i + 1);
			assertEquals(list0.first().getData().intValue(), i);
		}
	}

	@Test
	public void testAddLast() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			list0.addLast(i);
			assertEquals(list0.size(), i + 1);
			assertEquals(list0.last().getData().intValue(), i);
		}
	}

	@Test
	public void testAddBefore() {
		testAddFirst();
		Position<Integer> position = list0.last();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(list0.size(), SMALL_LIMIT + i);
			position = list0.addBefore(position, i);
			assertEquals(list0.size(), SMALL_LIMIT + i + 1);
			position = list0.before(position);
		}
		assertEquals(position, null);

	}

	@Test
	public void testAddAfter() {
		testAddLast();
		Position<Integer> position = list0.first();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(list0.size(), SMALL_LIMIT + i);
			position = list0.addAfter(position, i);
			assertEquals(list0.size(), SMALL_LIMIT + i + 1);
			position = list0.after(position);
		}
		assertEquals(position, null);
	}

	@Test
	public void testSet() {
		testAddLast();
		Position<Integer> position = list0.first();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(position.getData().intValue(), i);
			list0.set(position, 2 * i);
			assertEquals(position.getData().intValue(), 2 * i);
			position = list0.after(position);
		}
		assertEquals(position, null);
	}

	@Test
	public void testRemove() {
		testAddFirst();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(list0.remove(list0.last()).intValue(), i);
		}
		assertEquals(list0.last(), null);
	}

	@Test
	public void testIterator() {
		testAddLast();
		Iterator<Integer> iterator = list0.iterator();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(iterator.hasNext(), true);
			assertEquals(iterator.next().intValue(), i);
			iterator.remove();
			assertEquals(list0.size(), SMALL_LIMIT - i - 1);
		}
		assertEquals(iterator.hasNext(), false);
	}

	@Test
	public void testLimits() {
		for (int i = 0; i < LARGE_LIMIT; i++) {
			list0.addLast(i);
		}
		for (int i = 0; i < LARGE_LIMIT; i++) {
			list0.remove(list0.last());
		}
	}

}
