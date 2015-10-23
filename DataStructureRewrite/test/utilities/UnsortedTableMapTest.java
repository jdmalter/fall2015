package utilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacob Malter
 */
public class UnsortedTableMapTest {

	private static final int SMALL_LIMIT = 30;
	private static final int LARGE_LIMIT = 1000;
	// Takes ~22 seconds when LARGE_LIMIT = 100000
	// Takes ~.3 seconds when LARGE_LIMIT = 10000
	// Takes ~.03 seconds when LARGE_LIMIT = 1000

	private Map<Integer, Integer> map0;

	@Before
	public void setUp() throws Exception {
		testUnsortedMapTable();
	}

	@Test
	public void testUnsortedMapTable() {
		map0 = new UnsortedTableMap<Integer, Integer>();
		assertEquals(map0.size(), 0);
		assertEquals(map0.get(0), null);
		assertEquals(map0.remove(0), null);
		assertEquals(map0.entrySet().iterator().hasNext(), false);
	}

	@Test
	public void testGet() {
		testPut();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(map0.get(i).intValue(), 3 * i);
			assertEquals(map0.size(), SMALL_LIMIT);
		}
	}

	@Test
	public void testPut() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(map0.put(i, 2 * i), null);
			assertEquals(map0.size(), i + 1);
		}
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(map0.put(i, 3 * i).intValue(), 2 * i);
			assertEquals(map0.size(), SMALL_LIMIT);
		}
	}

	@Test
	public void testRemove() {
		testPut();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(map0.remove(i).intValue(), 3 * i);
			assertEquals(map0.size(), SMALL_LIMIT - i - 1);
		}
	}

	@Test
	public void testEntrySet() {
		testPut();
		Iterator<Entry<Integer, Integer>> iterator = map0.entrySet().iterator();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(iterator.hasNext(), true);
			assertEquals(iterator.next().getKey().intValue(), i);
		}
		assertEquals(iterator.hasNext(), false);
	}

	@Test
	public void testLimits() {
		for (int i = 0; i < LARGE_LIMIT; i++) {
			map0.put(i, 2 * i);
		}
		for (int i = 0; i < LARGE_LIMIT; i++) {
			map0.remove(i);
		}
	}

}