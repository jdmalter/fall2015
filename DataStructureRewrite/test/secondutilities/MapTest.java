package secondutilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MapTest {

	private static final int LIMIT = 100;
	private static final int SIZE = 50;

	private Map<Integer, Integer>[] maps;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		maps = (Map<Integer, Integer>[]) new Map[] {
				new ListMap<Integer, Integer>(),
				new LinkedHashMap<Integer, Integer>(),
				new LinkedProbeMap<Integer, Integer>(),
				new IdentityLinkedHashMap<Integer, Integer>() };
	}

	@Test
	public void testPut() {
		for (int i = 0; i < maps.length; i++) {
			assertEquals(maps[i].isEmpty(), true);
			for (int j = 0; j < SIZE; j++) {
				assertEquals(maps[i].size(), j);
				assertEquals(maps[i].put(j, j), null);
				assertEquals(maps[i].size(), j + 1);
			}
			for (int j = 0; j < SIZE; j++) {
				assertEquals(maps[i].size(), SIZE);
				assertEquals(maps[i].put(j, 2 * j).intValue(), j);
				assertEquals(maps[i].size(), SIZE);
			}
		}
	}

	@Test
	public void testGet() {
		testPut();
		for (int i = 0; i < maps.length; i++) {
			assertEquals(maps[i].get(-1), null);
			for (int j = 0; j < SIZE; j++) {
				assertEquals(maps[i].get(i).intValue(), 2 * i);
			}
		}
	}

	@Test
	public void testContainsKey() {
		testPut();
		for (int i = 0; i < maps.length; i++) {
			assertEquals(maps[i].containsKey(-1), false);
			for (int j = 0; j < SIZE; j++) {
				assertEquals(maps[i].containsKey(i), true);
			}
		}
	}

	@Test
	public void testRemove() {
		testPut();
		for (int i = 0; i < maps.length; i++) {
			assertEquals(maps[i].remove(-1), null);
			for (int j = 0; j < SIZE; j++) {
				assertEquals(maps[i].size(), SIZE - j);
				assertEquals(maps[i].remove(j).intValue(), 2 * j);
				assertEquals(maps[i].size(), SIZE - j - 1);
			}
			assertEquals(maps[i].remove(0), null);
		}
	}

	@Test
	public void testSize() {
		testPut();
	}

	@Test
	public void testIsEmpty() {
		testRemove();
	}

	@Test
	public void testKeyIterator() {
		testPut();
		for (int i = 0; i < maps.length; i++) {
			Iterator<Integer> iterator = maps[i].keyIterator();
			for (int j = 0; j < SIZE; j++) {
				assertEquals(iterator.hasNext(), true);
				assertEquals(2 * iterator.next().intValue(), 2 * j);
			}
			assertEquals(iterator.hasNext(), false);
			assertEquals(iterator.next(), null);
		}
	}

	@Test
	public void testSpeed() {
		for (int i = 0; i < maps.length; i++) {
			for (int j = 0; j < LIMIT; j++)
				maps[i].put(j, 2 * j);
			for (int j = 0; j < LIMIT; j++)
				maps[i].remove((3 * j) / 2);
			long start = System.nanoTime();
			for (int j = 0; j < LIMIT / 2; j++)
				maps[i].get(((3 * j) / 2) + 1);
			System.out.println(System.nanoTime() - start);
		}
	}
}