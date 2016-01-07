package secondutilities;

public interface PriorityQueue<K extends Comparable<K>, V> {

	void insert(K key, V value);

	K peekKey();
	
	V peekValue();

	V remove();

	int size();

}