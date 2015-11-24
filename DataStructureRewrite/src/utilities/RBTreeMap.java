package utilities;

import java.util.Comparator;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class RBTreeMap<K extends Comparable<? super K>, V> extends
		TreeMap<K, V> {

	public RBTreeMap() {
		super();
	}

	public RBTreeMap(Comparator<? super K> comparator) {
		super(comparator);
	}

	protected void rebalanceInsert(Position<Entry<K, V>> position) {
		if (tree.isRoot(position)) {
			tree.setAux(position, 1);
			resolveRed(position);
		}
	}

	private void resolveRed(Position<Entry<K, V>> position) {
		Position<Entry<K, V>> parent, uncle, middle, grand;
		parent = tree.parent(position);
		if (tree.getAux(parent) == 1) {
			uncle = tree.sibling(parent);
			if (tree.getAux(uncle) == 0) {
				middle = tree.restructure(position);
				tree.setAux(middle, 0);
				tree.setAux(tree.left(middle), 1);
				tree.setAux(tree.right(middle), 1);
			} else {
				tree.setAux(parent, 0);
				tree.setAux(uncle, 0);
				grand = tree.parent(parent);
				if (!tree.isRoot(grand)) {
					tree.setAux(grand, 1);
					resolveRed(grand);
				}
			}
		}
	}

	protected void rebalanceDelete(Position<Entry<K, V>> position) {
		if (tree.getAux(position) == 1)
			tree.setAux(position, 0);
		else if (!tree.isRoot(position)) {
			Position<Entry<K, V>> sib = tree.sibling(position);
			if (tree.isInternal(sib)
					&& (tree.getAux(sib) == 0 || tree
							.isInternal(tree.left(sib))))
				remedyDoubleBlack(position);
		}
	}

	private void remedyDoubleBlack(Position<Entry<K, V>> position) {
		Position<Entry<K, V>> parent = tree.parent(position);
		Position<Entry<K, V>> sibling = tree.sibling(position);
		if (tree.getAux(sibling) == 0) {
			if (tree.getAux(tree.left(sibling)) == 1
					|| tree.getAux(tree.right(sibling)) == 1) {
				Position<Entry<K, V>> child = (tree.getAux(tree.left(sibling)) == 1 ? tree
						.left(sibling) : tree.right(sibling));
				Position<Entry<K, V>> middle = tree.restructure(child);
				tree.setAux(middle, tree.getAux(parent) == 1 ? 1 : 0);
				tree.setAux(tree.left(middle), 0);
				tree.setAux(tree.right(middle), 0);
			} else {
				tree.setAux(sibling, 1);
				if (tree.getAux(parent) == 1) {
					tree.setAux(parent, 0);
				} else if (!tree.isRoot(parent))
					remedyDoubleBlack(parent);
			}
		} else {
			tree.rotate(sibling);
			tree.setAux(sibling, 0);
			tree.setAux(parent, 1);
			remedyDoubleBlack(position);
		}
	}
}