package utilities;


import utilities.Position;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public abstract class AbstractTree<E> implements Tree<E> {

	@Override
	public boolean isInternal(Position<E> position) {
		return sizeChildren(position) > 0;
	}

	@Override
	public boolean isExternal(Position<E> position) {
		return sizeChildren(position) == 0;
	}

	@Override
	public boolean isRoot(Position<E> position) {
		return position == root();
	}

	public int depth(Position<E> position) {
		if (isRoot(position))
			return 0;
		else
			return 1 + depth(parent(position));
	}

	public int height(Position<E> position) {
		int h = 0;
		Iterator<Position<E>> children = children(position).iterator();
		while (children.hasNext()) {
			Position<E> current = children.next();
			if (1 + height(current) > h)
				h = 1 + height(current);
		}
		return h;
	}

}