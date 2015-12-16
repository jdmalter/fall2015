package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public interface Iterator<E> {

	boolean hasNext();

	E next();

	void remove();

}