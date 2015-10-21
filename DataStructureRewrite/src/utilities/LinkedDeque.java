package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class LinkedDeque<E> implements Deque<E> {

	private PositionalList<E> list;

	public LinkedDeque() {
		list = new LinkedPositionalList<E>();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void addFirst(E element) {
		list.addFirst(element);
	}

	@Override
	public void addLast(E element) {
		list.addLast(element);
	}

	@Override
	public E removeFirst() {
		return list.first() == null ? null : list.remove(list.first());
	}

	@Override
	public E removeLast() {
		return list.last() == null ? null : list.remove(list.last());
	}

	@Override
	public E first() {
		return list.first() == null ? null : list.first().getData();
	}

	@Override
	public E last() {
		return list.last() == null ? null : list.last().getData();
	}

}