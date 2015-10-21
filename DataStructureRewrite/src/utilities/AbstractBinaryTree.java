package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements
		BinaryTree<E> {

	@Override
	public int sizeChildren(Position<E> position) {
		int count = 0;
		if (left(position) != null)
			count++;
		if (right(position) != null)
			count++;
		return count;
	}

	@Override
	public Position<E> sibling(Position<E> position) {
		Position<E> parent = parent(position);
		if (parent == null)
			return null;
		return (position == left(parent)) ? left(parent) : right(parent);
	}

	@Override
	public Position<E>[] children(Position<E> position) {
		@SuppressWarnings("unchecked")
		// wont add Position<T> or Position<!E>
		Position<E>[] result = (Position<E>[]) new Position[2];
		result[0] = left(position) == null ? null : left(position);
		result[1] = right(position) == null ? null : right(position);
		return result;
	}

}