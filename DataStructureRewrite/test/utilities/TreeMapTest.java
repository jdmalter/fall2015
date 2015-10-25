package utilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TreeMapTest {

	private static final int SMALL_LIMIT = 10;
	private static final int LARGE_LIMIT = 1000;
	// Code breaks after (20852 +- 2) put calls
	// Takes ~.4 seconds when LARGE_LIMIT = 10000
	// Takes ~.02 seconds when LARGE_LIMIT = 1000

	private SortedMap<Integer, Integer> sortedmap0;

	@Before
	public void setUp() throws Exception {
		testTreeMap();
	}

	@Test
	public void testTreeMap() {
		sortedmap0 = new TreeMap<Integer, Integer>();
		assertEquals(sortedmap0.size(), 0);
		assertEquals(sortedmap0.get(0), null);
		assertEquals(sortedmap0.remove(0), null);
		assertEquals(sortedmap0.entrySet().iterator().hasNext(), false);
		assertEquals(sortedmap0.firstEntry(), null);
		assertEquals(sortedmap0.lastEntry(), null);
		assertEquals(sortedmap0.ceilingEntry(0), null);
		assertEquals(sortedmap0.floorEntry(0), null);
		assertEquals(sortedmap0.higherEntry(0), null);
		assertEquals(sortedmap0.lowerEntry(0), null);
	}

	@Test
	public void testGet() {
		testPut();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.get(i).intValue(), 3 * i);
			assertEquals(sortedmap0.size(), SMALL_LIMIT);
		}
	}

	@Test
	public void testPut() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.put(i, 2 * i), null);
			assertEquals(sortedmap0.size(), i + 1);
		}
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.put(i, 3 * i).intValue(), 2 * i);
			assertEquals(sortedmap0.size(), SMALL_LIMIT);
		}
	}

	@Test
	public void testRemove() {
		testPut();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.remove(i).intValue(), 3 * i);
			assertEquals(sortedmap0.size(), SMALL_LIMIT - i - 1);
		}
	}

	@Test
	public void testTreeMax() {
		testPut();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			int val = sortedmap0.lastEntry().getKey().intValue();
			assertEquals(val, SMALL_LIMIT - i - 1);
			sortedmap0.remove(val);
		}
	}

	@Test
	public void testTreeMin() {
		testPut();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.firstEntry().getKey().intValue(), i);
			sortedmap0.remove(i);
		}
	}

	@Test
	public void testFirstEntry() {
		testPut();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.firstEntry().getKey().intValue(), i);
			sortedmap0.remove(i);
		}
	}

	@Test
	public void testLastEntry() {
		testPut();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			int val = sortedmap0.lastEntry().getKey().intValue();
			assertEquals(val, SMALL_LIMIT - i - 1);
			sortedmap0.remove(val);
		}
	}

	@Test
	public void testCeilingEntry() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.put(2 * i, 2 * i), null);
			assertEquals(sortedmap0.size(), i + 1);
		}
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.ceilingEntry((2 * i) - 1).getKey()
					.intValue(), 2 * i);
		}
	}

	@Test
	public void testFloorEntry() {
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.put(2 * i, 2 * i), null);
			assertEquals(sortedmap0.size(), i + 1);
		}
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(
					sortedmap0.floorEntry((2 * i) + 1).getKey().intValue(),
					2 * i);
		}
	}

	@Test
	public void testLowerEntry() {
		testPut();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.lowerEntry(i + 1).getKey().intValue(), i);
		}
	}

	@Test
	public void testHigherEntry() {
		testPut();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(sortedmap0.higherEntry(i - 1).getKey().intValue(), i);
		}
	}

	@Test
	public void testEntrySet() {
		testPut();
		Iterator<Entry<Integer, Integer>> iterator = sortedmap0.entrySet()
				.iterator();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(iterator.hasNext(), true);
			assertTrue(iterator.next().getValue() < 3 * (SMALL_LIMIT - 1) + 1);
		}
		assertEquals(iterator.hasNext(), false);
	}

	@Test
	public void testLimits() {
		for (int i = 0; i < LARGE_LIMIT; i++) {
			sortedmap0.put(i, 2 * i);
		}
		for (int i = 0; i < LARGE_LIMIT; i++) {
			sortedmap0.remove(i);
		}
	}

}