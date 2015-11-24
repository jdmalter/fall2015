package secondutilities;

public abstract class AbstractList<E> implements List<E> {

	@Override
	public boolean contains(E e) {
		return indexOf(e) >= 0;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

}