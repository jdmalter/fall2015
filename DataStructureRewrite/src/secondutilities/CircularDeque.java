package secondutilities;

public class CircularDeque<E> implements Deque<E> {

	private List<E> list;

	public CircularDeque() {
		list = new CircularArray<E>();
	}

	@Override
	public void addFirst(E e) {
		list.add(0, e);
	}

	@Override
	public void addLast(E e) {
		list.add(list.size(), e);
	}

	/**
	 * Somehow slower.
	 */
	@Override
	public E first() {
		return list.get(0);
	}

	@Override
	public E last() {
		return list.get(list.size() - 1);
	}

	@Override
	public E removeFirst() {
		return list.remove(0);
	}

	/**
	 * Slowest remove at tail.
	 */
	@Override
	public E removeLast() {
		return list.remove(list.size() - 1);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

}