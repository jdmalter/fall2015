package utilities;


import utilities.Position;

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
	public Iterable<Position<E>> children(Position<E> position) {
		List<Position<E>> result = new ArrayList<Position<E>>(2);
		if (left(position) != null)
			result.add(result.size(), left(position));
		if (right(position) != null)
			result.add(result.size(), right(position));
		return result;
	}

}