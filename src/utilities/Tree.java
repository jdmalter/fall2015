package utilities;


import utilities.Position;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public interface Tree<E> extends Iterable<E> {

	int size();

	Position<E> root();

	Position<E> parent(Position<E> position);

	Iterable<Position<E>> children(Position<E> position);

	int sizeChildren(Position<E> position);

	boolean isInternal(Position<E> position);

	boolean isExternal(Position<E> position);

	boolean isRoot(Position<E> position);

	Iterable<Position<E>> position();

}