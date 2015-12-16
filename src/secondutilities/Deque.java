package secondutilities;

public interface Deque<E> {

	void addFirst(E e);

	void addLast(E e);

	E first();

	E last();

	E removeFirst();

	E removeLast();

	int size();

	Iterator<E> iterator();

}