package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

	protected static class BalanceableBinaryTree<K, V> extends
			LinkedBinaryTree<Entry<K, V>> {

		protected static class BSTNode<E> extends Node<E> {

			private int aux;

			public BSTNode(E data) {
				super(data);
				aux = 0;
			}

			public int getAux() {
				return aux;
			}

			public void setAux(int aux) {
				this.aux = aux;
			}

		}

		public int getAux(Position<Entry<K, V>> position) {
			return ((BSTNode<Entry<K, V>>) position).getAux();
		}

		public void setAux(Position<Entry<K, V>> position, int value) {
			((BSTNode<Entry<K, V>>) position).setAux(value);
		}

		private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child,
				boolean isLeftChild) {
			child.setParent(parent);
			if (isLeftChild)
				parent.setLeft(child);
			else
				parent.setRight(child);
		}

		public void rotate(Position<Entry<K, V>> position) {
			Node<Entry<K, V>> x = super.validate(position);
			Node<Entry<K, V>> y = x.getParent();
			Node<Entry<K, V>> z = y.getParent();
			if (z == null) {
				root = x;
				x.setParent(null);
			} else
				relink(z, x, y == z.getLeft());
		}

		public Position<Entry<K, V>> restructure(Position<Entry<K, V>> child) {
			Position<Entry<K, V>> parent = parent(child);
			Position<Entry<K, V>> grandparent = parent(parent);
			if ((child == right(parent)) == (parent == right(grandparent))) {
				rotate(parent);
				return parent;
			} else {
				rotate(child);
				rotate(child);
				return child;
			}
		}

	}

	private static class Node<E> implements Position<E> {

		private E data;
		private Node<E> parent, left, right;

		public Node(E data) {
			this.data = data;
		}

		@Override
		public E getData() {
			return data;
		}

		public Node<E> getParent() {
			return parent;
		}

		public Node<E> getLeft() {
			return left;
		}

		public Node<E> getRight() {
			return right;
		}

		public void setData(E data) {
			this.data = data;
		}

		public void setParent(Node<E> parent) {
			this.parent = parent;
		}

		public void setLeft(Node<E> left) {
			this.left = left;
		}

		public void setRight(Node<E> right) {
			this.right = right;
		}

	}

	protected Node<E> root;
	private int size;

	public LinkedBinaryTree() {

	}

	private Node<E> validate(Position<E> position) {
		if (!(position instanceof Node))
			throw new IllegalArgumentException();
		Node<E> node = (Node<E>) position;
		if (node.getParent() == node)
			throw new IllegalArgumentException();
		return node;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Position<E> root() {
		return root;
	}

	@Override
	public Position<E> left(Position<E> position) {
		Node<E> node = validate(position);
		return node.getLeft();
	}

	@Override
	public Position<E> right(Position<E> position) {
		Node<E> node = validate(position);
		return node.getRight();
	}

	@Override
	public Position<E> parent(Position<E> position) {
		Node<E> node = validate(position);
		return node.getParent();
	}

	@Override
	public Position<E> addRoot(E data) {
		if (size() != 0)
			throw new IllegalStateException();
		root = new Node<E>(data);
		size = 1;
		return root;
	}

	@Override
	public Position<E> addLeft(Position<E> position, E data) {
		Node<E> node = validate(position);
		if (node.getLeft() != null)
			throw new IllegalArgumentException();
		Node<E> insert = new Node<E>(data);
		insert.setParent(node);
		node.setLeft(insert);
		size++;
		return insert;
	}

	@Override
	public Position<E> addRight(Position<E> position, E data) {
		Node<E> node = validate(position);
		if (node.getRight() != null)
			throw new IllegalArgumentException();
		Node<E> insert = new Node<E>(data);
		insert.setParent(node);
		node.setRight(insert);
		size++;
		return insert;
	}

	@Override
	public E set(Position<E> position, E data) {
		Node<E> node = validate(position);
		E result = node.getData();
		node.setData(data);
		return result;
	}

	@Override
	public E remove(Position<E> position) {
		Node<E> node = validate(position);
		if (sizeChildren(position) == 2)
			throw new IllegalArgumentException();
		Node<E> child = (node.getLeft() != null) ? node.getLeft() : node
				.getRight();
		if (child != null)
			child.setParent(node.getParent());
		if (node == root())
			root = child;
		else {
			Node<E> parent = node.getParent();
			if (node == parent.getLeft())
				parent.setLeft(child);
			else
				parent.setRight(child);
		}
		size--;
		E result = node.getData();
		node.setData(null);
		node.setParent(null);
		node.setLeft(null);
		node.setRight(null);
		node = null;
		return result;
	}

	private class ElementIterator implements Iterator<E> {

		Iterator<Position<E>> positionIterator;

		public ElementIterator() {
			positionIterator = position().iterator();
		}

		@Override
		public boolean hasNext() {
			return positionIterator.hasNext();
		}

		@Override
		public E next() {
			return positionIterator.next().getData();
		}

		@Override
		public void remove() {
			positionIterator.remove();
		}

	}

	private void preorderSubtree(Position<E> position, List<Position<E>> list) {
		list.add(list.size(), position);
		if (left(position) != null)
			inorderSubtree(left(position), list);
		if (right(position) != null)
			inorderSubtree(right(position), list);
	}

	public Iterable<Position<E>> preorder() {
		List<Position<E>> result = new ArrayList<Position<E>>();
		if (size() != 0)
			preorderSubtree(root(), result);
		return result;
	}

	private void postorderSubtree(Position<E> position, List<Position<E>> list) {
		if (left(position) != null)
			inorderSubtree(left(position), list);
		if (right(position) != null)
			inorderSubtree(right(position), list);
		list.add(list.size(), position);
	}

	public Iterable<Position<E>> postorder() {
		List<Position<E>> result = new ArrayList<Position<E>>();
		if (size() != 0)
			postorderSubtree(root(), result);
		return result;
	}

	private void inorderSubtree(Position<E> position, List<Position<E>> list) {
		if (left(position) != null)
			inorderSubtree(left(position), list);
		list.add(list.size(), position);
		if (right(position) != null)
			inorderSubtree(right(position), list);
	}

	public Iterable<Position<E>> inorder() {
		List<Position<E>> result = new ArrayList<Position<E>>();
		if (size() != 0)
			inorderSubtree(root(), result);
		return result;
	}

	@Override
	public Iterable<Position<E>> position() {
		return inorder();
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}

}