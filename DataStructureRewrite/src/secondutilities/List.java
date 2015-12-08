package secondutilities;

public interface List<E> {

	void add(int index, E e);

	E set(int index, E e);

	int indexOf(E e);

	E get(int index);

	E remove(int index);

	int size();

	Iterator<E> iterator();

	List<E> subList(int startIndex, int endIndex);

}