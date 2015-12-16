package utilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacob Malter
 */
public class ArrayListTest {

	private static final int SMALL_LIMIT = 30;
	private static final int LARGE_LIMIT = 100000;
	// Takes ~685 seconds when LARGE_LIMIT = 1000000
	// Takes ~3.5 seconds when LARGE_LIMIT = 100000
	// Takes ~.1 seconds when LARGE_LIMIT = 10000
	// Takes ~.016 seconds when LARGE_LIMIT = 1000

	private List<Integer> list0;

	@Before
	public void setUp() throws Exception {
		testArrayList();
	}

	@Test
	public void testArrayList() {
		list0 = new ArrayList<Integer>();
		assertEquals(list0.size(), 0);
	}

	@Test
	public void testSet() {
		testAdd();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			list0.set(i, 2 * i);
			assertEquals(list0.get(i).intValue(), 2 * i);
		}
	}

	@Test
	public void testAdd() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			list0.add(0, i);
			assertEquals(list0.get(0).intValue(), i);
		}
	}

	@Test
	public void testRemove() {
		testAdd();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(list0.remove(0).intValue(), SMALL_LIMIT - i - 1);
		}
	}

	@Test
	public void testIterator() {
		testAdd();
		Iterator<Integer> iterator = list0.iterator();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(iterator.hasNext(), true);
			assertEquals(iterator.next().intValue(), SMALL_LIMIT - i - 1);
			iterator.remove();
			assertEquals(list0.size(), SMALL_LIMIT - i - 1);
		}
		assertEquals(iterator.hasNext(), false);
	}

	@Test
	public void testLimits() {
		for (int i = 0; i < LARGE_LIMIT; i++) {
			list0.add(i, i);
		}
		for (int i = 0; i < LARGE_LIMIT; i++) {
			list0.remove(0);
		}
	}

}