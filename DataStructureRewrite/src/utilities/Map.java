package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public interface Map<K, V> {

	int size();

	V get(K key);

	V put(K key, V value);

	V remove(K key);

	Iterable<K> keySet();

	Iterable<V> values();

	Iterable<Entry<K, V>> entrySet();

}