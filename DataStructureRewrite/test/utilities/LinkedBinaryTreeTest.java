package utilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacob Malter
 */
public class LinkedBinaryTreeTest {

	private static final int SMALL_LIMIT = 10;
	private static final int LARGE_LIMIT = 10000;
	// Takes ~315 seconds when LARGE_LIMIT = 100000000
	// At ~315 seconds, garbage collector overhead limit exceeded
	// Takes ~18 seconds when LARGE_LIMIT = 10000000
	// Takes ~1.6 seconds when LARGE_LIMIT = 1000000
	// Takes ~.015 seconds when LARGE_LIMIT = 100000
	// Takes ~.005 seconds when LARGE_LIMIT = 10000

	private BinaryTree<Integer> binarytree0;

	@Before
	public void setUp() throws Exception {
		testLinkedBinaryTree();
	}

	@Test
	public void testLinkedBinaryTree() {
		binarytree0 = new LinkedBinaryTree<Integer>();
		assertEquals(binarytree0.size(), 0);
		assertEquals(binarytree0.root(), null);
	}

	@Test
	public void testAddRoot() {
		Position<Integer> root = binarytree0.addRoot(0);
		assertEquals(binarytree0.size(), 1);
		assertEquals(binarytree0.root(), root);
		assertEquals(binarytree0.parent(binarytree0.root()), null);
		try {
			binarytree0.addRoot(1);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(binarytree0.size(), 1);
			assertEquals(binarytree0.root(), root);
		}
	}

	@Test
	public void testAddLeft() {
		Position<Integer> position = binarytree0.addRoot(0);
		for (int i = 1; i < SMALL_LIMIT; i++) {
			binarytree0.addLeft(position, i);
			position = binarytree0.left(position);
			assertEquals(binarytree0.size(), i + 1);
		}
		try {
			binarytree0.addLeft(binarytree0.root(), 100);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(binarytree0.size(), SMALL_LIMIT);
		}
	}

	@Test
	public void testAddRight() {
		Position<Integer> position = binarytree0.addRoot(0);
		for (int i = 1; i < SMALL_LIMIT; i++) {
			binarytree0.addRight(position, i);
			position = binarytree0.right(position);
			assertEquals(binarytree0.size(), i + 1);
		}
		try {
			binarytree0.addRight(binarytree0.root(), 100);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(binarytree0.size(), SMALL_LIMIT);
		}
	}

	@Test
	public void testSet() {
		testAddLeft();
		Position<Integer> position = binarytree0.root();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(position.getData().intValue(), i);
			binarytree0.set(position, 2 * i);
			assertEquals(position.getData().intValue(), 2 * i);
			position = binarytree0.left(position);
		}
	}

	@Test
	public void testRemove() {
		testAddRight();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			assertEquals(binarytree0.remove(binarytree0.root()).intValue(), i);
		}
		testAddRight();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			Position<Integer> position = binarytree0.root();
			for (int j = 0; j < SMALL_LIMIT - i - 1; j++)
				position = binarytree0.right(position);
			assertEquals(binarytree0.remove(position).intValue(), SMALL_LIMIT
					- i - 1);
		}
		testAddLeft();
		for (int i = 0; i < SMALL_LIMIT; i++) {
			Position<Integer> position = binarytree0.root();
			for (int j = 0; j < SMALL_LIMIT - i - 1; j++)
				position = binarytree0.left(position);
			assertEquals(binarytree0.remove(position).intValue(), SMALL_LIMIT
					- i - 1);
		}
	}

	@Test
	public void testLimits() {
		Position<Integer> position = binarytree0.addRoot(0);
		for (int i = 1; i < LARGE_LIMIT; i++) {
			position = binarytree0.addRight(position, i);
		}
		position = binarytree0.root();
		for (int i = 0; i < LARGE_LIMIT; i++) {
			position = binarytree0.addLeft(position, i);
		}
	}

}