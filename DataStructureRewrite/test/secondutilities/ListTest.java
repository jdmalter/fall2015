package secondutilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ListTest {

	private static final int INVALID_INT = 255;
	private static final Integer INVALID = new Integer(INVALID_INT);
	private static final int SIZE = 42;
	private static final int LIMIT = 1000;

	private List<Integer>[] lists;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		lists = (List<Integer>[]) new List[] { new ArrayList<Integer>(),
				new LinkedList<Integer>(), new CircularArray<Integer>() };
	}

	@Test
	public void testAdd() {
		for (int i = 0; i < lists.length; i++) {
			assertEquals(lists[i].size(), 0);
			lists[i].add(-1, INVALID);
			assertEquals(lists[i].size(), 0);
			lists[i].add(1, INVALID);
			assertEquals(lists[i].size(), 0);
			lists[i].add(0, null);
			assertEquals(lists[i].size(), 0);
			for (int j = 0; j < SIZE / 3; j++) {
				assertEquals(lists[i].size(), j);
				lists[i].add(0, (SIZE / 3) - j - 1);
				assertEquals(lists[i].size(), j + 1);
			}
			for (int j = 0; j < SIZE / 3; j++) {
				assertEquals(lists[i].size(), (SIZE / 3) + j);
				lists[i].add(lists[i].size(), 2 * (SIZE / 3) + j);
				assertEquals(lists[i].size(), (SIZE / 3) + j + 1);
			}
			for (int j = 0; j < SIZE / 3; j++) {
				assertEquals(lists[i].size(), 2 * (SIZE / 3) + j);
				lists[i].add((SIZE / 3) + j, (SIZE / 3) + j);
				assertEquals(lists[i].size(), 2 * (SIZE / 3) + j + 1);
			}
		}
	}

	@Test
	public void testSet() {
		testAdd();
		for (int i = 0; i < lists.length; i++) {
			assertEquals(lists[i].set(-1, INVALID), null);
			assertEquals(lists[i].set(SIZE, INVALID), null);
			assertEquals(lists[i].set(0, null), null);
			for (int j = 0; j < 3 * (SIZE / 3); j++) {
				assertEquals(lists[i].set(j, 2 * j).intValue(), j);
			}
		}
	}

	@Test
	public void testIndexOf() {
		testSet();
		for (int i = 0; i < lists.length; i++) {
			assertEquals(lists[i].indexOf(INVALID), -1);
			assertEquals(lists[i].indexOf(null), -1);
			for (int j = 0; j < 3 * (SIZE / 3); j++)
				assertEquals(lists[i].indexOf(2 * j), j);
		}
	}

	@Test
	public void testContains() {
		testAdd();
		for (int i = 0; i < lists.length; i++) {
			assertEquals(lists[i].contains(INVALID), false);
			assertEquals(lists[i].indexOf(null), -1);
			assertEquals(lists[i].contains(0), true);
		}
	}

	@Test
	public void testGet() {
		testSet();
		for (int i = 0; i < lists.length; i++) {
			assertEquals(lists[i].get(-1), null);
			assertEquals(lists[i].get(SIZE), null);
			for (int j = 0; j < 3 * (SIZE / 3); j++)
				assertEquals(lists[i].get(j).intValue(), 2 * j);
		}
	}

	@Test
	public void testRemove() {
		testSet();
		for (int i = 0; i < lists.length; i++) {
			assertEquals(lists[i].remove(-1), null);
			assertEquals(lists[i].remove(SIZE), null);
			for (int j = 0; j < SIZE / 3; j++)
				assertEquals(lists[i].remove(0).intValue(), 2 * j);
			for (int j = 0; j < SIZE / 3; j++)
				assertEquals(lists[i].remove(SIZE / 3).intValue(),
						2 * (2 * (SIZE / 3) + j));
			for (int j = 0; j < SIZE / 3; j++)
				assertEquals(lists[i].remove(lists[i].size() - 1).intValue(),
						2 * (2 * (SIZE / 3) - j - 1));
		}
	}

	@Test
	public void testSize() {
		testAdd();
	}

	@Test
	public void testIsEmpty() {
		for (int i = 0; i < lists.length; i++) {
			assertEquals(lists[i].isEmpty(), true);
		}
		testSize();
		for (int i = 0; i < lists.length; i++) {
			assertEquals(lists[i].isEmpty(), false);
		}
	}

	@Test
	public void testIterator() {
		testSet();
		for (int i = 0; i < lists.length; i++) {
			Iterator<Integer> iterator = lists[i].iterator();
			assertEquals(iterator.hasNext(), true);
			for (int j = 0; j < 3 * (SIZE / 3); j++)
				assertEquals(iterator.next().intValue(), 2 * j);
			assertEquals(iterator.next(), null);
			assertEquals(iterator.hasNext(), false);
		}
	}

	@Test
	public void testSubList() {
		testSet();
		for (int i = 0; i < lists.length; i++) {
			assertEquals(lists[i].subList(1, 0), null);
			assertEquals(lists[i].subList(-1, 0), null);
			assertEquals(lists[i].subList(0, -1), null);
			assertEquals(lists[i].subList(SIZE - 1, SIZE), null);
			assertEquals(lists[i].subList(SIZE, SIZE), null);
			List<Integer> list = lists[i].subList(SIZE / 3, 2 * (SIZE / 3));
			for (int j = 0; j < SIZE / 3; j++)
				assertEquals(list.get(j).intValue(), 2 * ((SIZE / 3) + j));
		}
	}

	@Test
	public void testSpeed() {
		for (int i = 0; i < lists.length; i++) {
			for (int j = 0; j < LIMIT; j++)
				lists[i].add(0, i);
			long start = System.nanoTime();
			for (int j = 0; j < LIMIT; j++)
				lists[i].remove(lists[i].size() / 2);
			System.out.println(System.nanoTime() - start);
		}
	}

}