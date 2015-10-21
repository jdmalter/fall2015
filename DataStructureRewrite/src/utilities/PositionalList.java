package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public interface PositionalList<E> {

	int size();

	Position<E> first();

	Position<E> last();

	Position<E> before(Position<E> position);

	Position<E> after(Position<E> position);

	Position<E> addFirst(E data);

	Position<E> addLast(E data);

	Position<E> addBefore(Position<E> position, E data);

	Position<E> addAfter(Position<E> position, E data);

	E set(Position<E> position, E data);

	E remove(Position<E> position);

}