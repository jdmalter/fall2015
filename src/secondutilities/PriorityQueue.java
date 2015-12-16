package secondutilities;

public interface PriorityQueue<K extends Comparable<K>, V> {

	void insert(K key, V value);

	V peek();

	V remove();

	int size();

}