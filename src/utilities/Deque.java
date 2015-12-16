package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public interface Deque<E> extends Iterable<E> {

	int size();

	void addFirst(E element);

	void addLast(E element);

	E removeFirst();

	E removeLast();

	E first();

	E last();

}