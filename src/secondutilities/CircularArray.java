package secondutilities;

public class CircularArray<E> implements List<E> {

	private static final int INITIAL_CAPACITY = 31;

	private E[] elements;
	private int size, head;

	@SuppressWarnings("unchecked")
	public CircularArray() {
		elements = (E[]) new Object[INITIAL_CAPACITY];
	}

	/**
	 * Slowest insert of every implementation.
	 */
	@Override
	public void add(int index, E e) {
		if (index < 0 || index > size)
			return;

		if (size == elements.length - 1) {
			@SuppressWarnings("unchecked")
			E[] newElements = (E[]) new Object[(elements.length << 1) + 1];

			for (int i = 0; i < elements.length; i++)
				newElements[(i + head) % newElements.length] = elements[translate(i)];
			elements = newElements;
		}

		if (index == 0) {
			if (head == 0) {
				head = elements.length - 1;
				elements[head] = e;
				size++;

			} else {
				elements[--head] = e;
				size++;

			}

		} else {
			for (int i = size - 1; i >= index; i--)
				elements[translate(i + 1)] = elements[translate(i)];
			elements[translate(index)] = e;
			size++;
		}
	}

	@Override
	public E set(int index, E e) {
		if (index < 0 || index >= size)
			return null;
		E result = elements[translate(index)];

		elements[translate(index)] = e;
		return result;
	}

	/**
	 * Slowest for every list implementation.
	 */
	@Override
	public int indexOf(E e) {
		for (int i = 0; i < size; i++)
			if (elements[translate(i)] == null ? e == null
					: elements[translate(i)].equals(e))
				return i;
		return -1;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size)
			return null;
		return elements[translate(index)];
	}

	/**
	 * Slowest removal at middle.
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size)
			return null;
		E result = elements[translate(index)];

		if (index == 0) {
			if (head == elements.length - 1) {
				elements[elements.length - 1] = null;
				head = 0;
				size--;
				return result;

			} else {
				elements[head++] = null;
				size--;
				return result;
			}

		} else {
			for (int i = index; i < size - 1; i++)
				elements[translate(i)] = elements[translate(i + 1)];
			elements[translate(--size)] = null;
			return result;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			private int cursor = 0;

			@Override
			public boolean hasNext() {
				return cursor < size;
			}

			@Override
			public E next() {
				if (!hasNext())
					return null;
				return elements[translate(cursor++)];
			}

		};
	}

	@Override
	public List<E> subList(int startIndex, int endIndex) {
		if (startIndex > endIndex || startIndex < 0 || startIndex >= size
				|| endIndex >= size)
			return null;
		List<E> list = new CircularArray<E>();

		for (int i = startIndex; i <= endIndex; i++)
			list.add(i - startIndex, get(i));
		return list;
	}

	private int translate(int index) {
		return (index + head) % elements.length;
	}

}