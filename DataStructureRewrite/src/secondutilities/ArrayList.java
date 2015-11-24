package secondutilities;

public class ArrayList<E> extends AbstractList<E> {

	private static final int INITIAL_CAPACITY = 31;

	private E[] elements;
	private int size;

	@SuppressWarnings("unchecked")
	public ArrayList() {
		elements = (E[]) new Object[INITIAL_CAPACITY];
	}

	/**
	 * Unusually bad performance for inserting at head compared to other lists.
	 * Otherwise, as fast as CircularArray and faster than linked list.
	 */
	@Override
	public void add(int index, E e) {
		if (index < 0 || index > size || e == null)
			return;

		if (size == elements.length - 1) {
			@SuppressWarnings("unchecked")
			E[] newElements = (E[]) new Object[(elements.length << 1) + 1];

			for (int i = 0; i < elements.length; i++)
				newElements[i] = elements[i];
			elements = newElements;
		}

		for (int i = size - 1; i >= index; i--)
			elements[i + 1] = elements[i];
		elements[index] = e;
		size++;
	}

	@Override
	public E set(int index, E e) {
		if (index < 0 || index >= size || e == null)
			return null;
		E result = elements[index];

		elements[index] = e;
		return result;
	}

	@Override
	public int indexOf(E e) {
		if (e == null)
			return -1;

		for (int i = 0; i < size; i++)
			if (elements[i].equals(e))
				return i;
		return -1;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size)
			return null;
		return elements[index];
	}

	/**
	 * Much slower removal at head. Fastest removal at tail.
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size)
			return null;
		E result = elements[index];

		for (int i = index; i < size - 1; i++)
			elements[i] = elements[i + 1];
		elements[--size] = null;
		return result;
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
				return elements[cursor++];
			}

		};
	}

	@Override
	public List<E> subList(int startIndex, int endIndex) {
		if (startIndex > endIndex || startIndex < 0 || startIndex >= size
				|| endIndex >= size)
			return null;
		List<E> list = new ArrayList<E>();

		for (int i = startIndex; i <= endIndex; i++)
			list.add(i - startIndex, get(i));
		return list;
	}

}