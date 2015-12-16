package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public interface List<E> extends Iterable<E> {

	int size();

	E get(int index);

	E set(int index, E element);

	void add(int index, E element);

	E remove(int index);

}