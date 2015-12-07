package secondutilities;

public interface Map<K, V> {

	V put(K key, V value);

	V get(K key);

	boolean containsKey(K key);

	V remove(K key);

	int size();

	boolean isEmpty();

	Iterator<K> keyIterator();

}