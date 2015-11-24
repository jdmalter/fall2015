package secondutilities;

public class LinkedList<E> extends AbstractList<E> {

	private static class Node<E> {

		private E e;
		private Node<E> prev, next;

		private Node(E e) {
			this.e = e;
		}

	}

	private Node<E> head, tail;
	private int size;

	public LinkedList() {

	}

	/**
	 * Best speed for insert at head and tail.
	 */
	@Override
	public void add(int index, E e) {
		if (index < 0 || index > size || e == null)
			return;
		Node<E> newNode = new Node<E>(e);

		if (size == 0) {
			head = newNode;
			tail = head;
			size++;

		} else if (index == 0) {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
			size++;

		} else if (index == size) {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
			size++;

		} else {
			Node<E> current = head;
			for (int i = 0; i < index; i++)
				current = current.next;

			newNode.prev = current.prev;
			newNode.next = current;

			newNode.prev.next = newNode;
			newNode.next.prev = newNode;
			size++;
		}
	}

	/**
	 * Much slower than other implementaions.
	 */
	@Override
	public E set(int index, E e) {
		if (index < 0 || index >= size || e == null)
			return null;
		Node<E> current = head;

		for (int i = 0; i < index; i++)
			current = current.next;
		E result = current.e;

		current.e = e;
		return result;
	}

	@Override
	public int indexOf(E e) {
		if (e == null)
			return -1;
		int index = 0;

		for (Node<E> current = head; current != null; current = current.next, index++)
			if (current.e.equals(e))
				return index;
		return -1;
	}

	/**
	 * Much slower than other implementations.
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size)
			return null;
		if (index == size - 1)
			return tail.e;
		Node<E> current = head;

		for (int i = 0; i < index; i++)
			current = current.next;
		return current.e;
	}

	/**
	 * Fastest removal at head and tail. Remove at head faster than ArrayList
	 * remove at head.
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size)
			return null;
		E result = null;

		if (size == 1) {
			result = head.e;
			head = null;
			tail = null;
			size--;

		} else if (index == 0) {
			result = head.e;
			head = head.next;
			head.prev = null;
			size--;

		} else if (index == size - 1) {
			result = tail.e;
			tail = tail.prev;
			tail.next = null;
			size--;

		} else {
			Node<E> current = head;
			for (int i = 0; i < index; i++)
				current = current.next;
			result = current.e;

			current.prev.next = current.next;
			current.next.prev = current.prev;

			current = null;
			size--;
		}
		return result;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			private Node<E> cursor = head;

			@Override
			public boolean hasNext() {
				return cursor != null;
			}

			@Override
			public E next() {
				if (!hasNext())
					return null;

				E result = cursor.e;
				cursor = cursor.next;
				return result;
			}

		};
	}

	@Override
	public List<E> subList(int startIndex, int endIndex) {
		if (startIndex > endIndex || startIndex < 0 || startIndex >= size
				|| endIndex >= size)
			return null;
		List<E> list = new LinkedList<E>();
		int index = 0;
		Node<E> current = head;

		while (index <= endIndex) {
			if (startIndex <= index)
				list.add(index - startIndex, current.e);
			current = current.next;
			index++;
		}
		return list;
	}

}