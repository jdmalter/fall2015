package utilities;

import java.util.Comparator;

import utilities.LinkedBinaryTree.BalanceableBinaryTree;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class TreeMap<K extends Comparable<? super K>, V> extends
		AbstractSortedMap<K, V> {

	protected BalanceableBinaryTree<K, V> tree;

	public TreeMap() {
		super();
		tree = new BalanceableBinaryTree<K, V>();
		tree.addRoot(null);
	}

	public TreeMap(Comparator<? super K> comparator) {
		super(comparator);
		tree = new BalanceableBinaryTree<K, V>();
		tree.addRoot(null);
	}

	@Override
	public int size() {
		return (tree.size() - 1) / 2;
	}

	private void expandExternal(Position<Entry<K, V>> position,
			Entry<K, V> entry) {
		tree.set(position, entry);
		tree.addLeft(position, null);
		tree.addRight(position, null);
	}

	private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> position,
			K key) {
		if (tree.isExternal(position))
			return position;
		int comparison = compare(key, position.getData().getKey());
		if (comparison == 0)
			return position;
		else if (comparison < 0)
			return treeSearch(tree.left(position), key);
		else
			return treeSearch(tree.right(position), key);
	}

	protected void rebalanceAccess(Position<Entry<K, V>> position) {
		// Should do nothing; intended to be overridden by subclass
	}

	protected void rebalanceInsert(Position<Entry<K, V>> position) {
		// Should do nothing; intended to be overridden by subclass
	}

	protected void rebalanceDelete(Position<Entry<K, V>> position) {
		// Should do nothing; intended to be overridden by subclass
	}

	@Override
	public V get(K key) {
		Position<Entry<K, V>> position = treeSearch(tree.root(), key);
		rebalanceAccess(position);
		if (tree.isExternal(position))
			return null;
		return position.getData().getValue();
	}

	@Override
	public V put(K key, V value) {

		Entry<K, V> newEntry = new MapEntry<K, V>(key, value);
		Position<Entry<K, V>> position = treeSearch(tree.root(), key);
		if (tree.isExternal(position)) {
			expandExternal(position, newEntry);
			rebalanceInsert(position);
			return null;
		} else {
			V result = position.getData().getValue();
			tree.set(position, newEntry);
			rebalanceAccess(position);
			return result;
		}
	}

	@Override
	public V remove(K key) {
		Position<Entry<K, V>> position = treeSearch(tree.root(), key);
		if (tree.isExternal(position)) {
			rebalanceAccess(position);
			return null;
		} else {
			V result = position.getData().getValue();
			if (tree.isExternal(tree.left(position))
					&& tree.isExternal(tree.right(position))) {
				Position<Entry<K, V>> replacement = treeMax(tree.left(position));
				tree.set(position, replacement.getData());
				position = replacement;
			}
			Position<Entry<K, V>> leaf = (tree.isExternal(tree.left(position)) ? tree
					.left(position) : tree.right(position));
			Position<Entry<K, V>> sibling = tree.sibling(leaf);
			tree.remove(leaf);
			tree.remove(position);
			rebalanceDelete(sibling);
			return result;
		}
	}

	protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> position) {
		Position<Entry<K, V>> walk = position;
		while (tree.isInternal(walk))
			walk = tree.right(walk);
		return tree.parent(walk);
	}

	protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> position) {
		Position<Entry<K, V>> walk = position;
		while (tree.isInternal(walk))
			walk = tree.left(walk);
		return tree.parent(walk);
	}

	@Override
	public Entry<K, V> firstEntry() {
		if (size() == 0)
			return null;
		return treeMin(tree.root()).getData();
	}

	@Override
	public Entry<K, V> lastEntry() {
		if (size() == 0)
			return null;
		return treeMax(tree.root()).getData();
	}

	@Override
	public Entry<K, V> ceilingEntry(K key) {
		Position<Entry<K, V>> position = treeSearch(tree.root(), key);
		if (tree.isInternal(position))
			return position.getData();
		while (!tree.isRoot(position))
			if (position == tree.left(tree.parent(position)))
				return tree.parent(position).getData();
			else
				position = tree.parent(position);
		return null;
	}

	@Override
	public Entry<K, V> floorEntry(K key) {
		Position<Entry<K, V>> position = treeSearch(tree.root(), key);
		if (tree.isInternal(position))
			return position.getData();
		while (!tree.isRoot(position))
			if (position == tree.right(tree.parent(position)))
				return tree.parent(position).getData();
			else
				position = tree.parent(position);
		return null;
	}

	@Override
	public Entry<K, V> lowerEntry(K key) {
		Position<Entry<K, V>> position = treeSearch(tree.root(), key);
		if (tree.isInternal(position) && tree.isInternal(tree.left(position)))
			return treeMax(tree.left(position)).getData();
		while (!tree.isRoot(position))
			if (position == tree.right(tree.parent(position)))
				return tree.parent(position).getData();
			else
				position = tree.parent(position);
		return null;
	}

	@Override
	public Entry<K, V> higherEntry(K key) {
		Position<Entry<K, V>> position = treeSearch(tree.root(), key);
		if (tree.isInternal(position) && tree.isInternal(tree.right(position)))
			return treeMin(tree.right(position)).getData();
		while (!tree.isRoot(position))
			if (position == tree.left(tree.parent(position)))
				return tree.parent(position).getData();
			else
				position = tree.parent(position);
		return null;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> buffer = new ArrayList<Entry<K, V>>(size());
		Iterator<Position<Entry<K, V>>> inorderIterator = tree.inorder()
				.iterator();
		while (inorderIterator.hasNext()) {
			Position<Entry<K, V>> current = inorderIterator.next();
			if (tree.isInternal(current))
				buffer.add(buffer.size(), current.getData());
		}
		return buffer;
	}

}