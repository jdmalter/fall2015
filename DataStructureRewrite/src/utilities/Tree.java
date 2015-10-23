package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public interface Tree<E> {

	int size();

	Position<E> root();

	Position<E> parent(Position<E> position);

	Position<E>[] children(Position<E> position);

	int sizeChildren(Position<E> position);

	boolean isInternal(Position<E> position);

	boolean isExternal(Position<E> position);

	boolean isRoot(Position<E> position);

}