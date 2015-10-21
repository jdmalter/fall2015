package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public interface BinaryTree<E> extends Tree<E> {

	Position<E> left(Position<E> position);
	
	Position<E> right(Position<E> position);
	
	Position<E> sibling(Position<E> position);
	
	Position<E> addRoot(E data);
	
	Position<E> addLeft(Position<E> position, E data);
	
	Position<E> addRight(Position<E> position, E data);
	
	E set(Position<E> position, E data);
	
	E remove(Position<E> position);
	
}