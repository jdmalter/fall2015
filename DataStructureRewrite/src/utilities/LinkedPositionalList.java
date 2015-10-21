package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class LinkedPositionalList<E> implements PositionalList<E> {

	private static class Node<E> implements Position<E> {

		private Node<E> next, prev;
		private E data;
		
		public Node(E data) {
			this.data = data;
		}
		
		@Override
		public E getData() {
			if (next == null || prev ==  null)
				throw new IllegalStateException();
			return data;
		}
	
		public Node<E> getPrev() {
			return prev;
		}
		
		public Node<E> getNext() {
			return next;
		}
		
		public void setData(E data) {
			this.data = data;
		}
		
		public void setPrev(Node<E> node) {
			prev = node;
		}
		
		public void setNext(Node<E> node) {
			next = node;
		}
		
	}
	
	private Node<E> head, tail;
	private int size;
	
	public LinkedPositionalList() {
		head = new Node<E>(null);
		tail = new Node<E>(null);
		tail.setPrev(head);
		head.setNext(tail);
	}
	
	@Override
	public int size() {
		return size;
	}

	private Node<E> validate(Position<E> position) {
		if (!(position instanceof Node))
			throw new IllegalArgumentException();
		Node<E> node = (Node<E>) position;
		if (node == head || node == tail)
			return null;
		return node;
	}
	
	private Position<E> position(Node<E> node) {
		if (node == head || node == tail)
			return null;
		return node;
	}
	
	@Override
	public Position<E> first() {
		return position(head.getNext());
	}

	@Override
	public Position<E> last() {
		return position(tail.getPrev());
	}

	@Override
	public Position<E> before(Position<E> position) {
		Node<E> node = validate(position);
		return position(node.getPrev());
	}

	@Override
	public Position<E> after(Position<E> position) {
		Node<E> node = validate(position);
		return position(node.getNext());
	}

	private Position<E> addBetween(Node<E> first, Node<E> second, E data) {
		Node<E> insert = new Node<E>(data);
		insert.setPrev(first);
		insert.setNext(second);
		first.setNext(insert);
		second.setPrev(insert);
		size++;
		return insert;
	}
	
	@Override
	public Position<E> addFirst(E data) {
		return addBetween(head, head.getNext(), data);
	}

	@Override
	public Position<E> addLast(E data) {
		return addBetween(tail.getPrev(), tail, data);
	}

	@Override
	public Position<E> addBefore(Position<E> position, E data) {
		Node<E> node = validate(position);
		return addBetween(node.getPrev(), node, data);
	}

	@Override
	public Position<E> addAfter(Position<E> position, E data) {
		Node<E> node = validate(position);
		return addBetween(node, node.getNext(), data);
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
		Node<E> prev = node.getPrev();
		Node<E> next = node.getNext();
		prev.setNext(next);
		next.setPrev(prev);
		size--;
		E result = node.getData();
		node.setData(null);
		node.setNext(null);
		node.setPrev(null);
		node = null;
		return result;
	}

}