package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public interface PriorityQueue<K extends Comparable<? super K>, V> {

	int size();

	Entry<K, V> insert(K key, V value);

	Entry<K, V> min();

	Entry<K, V> removeMin();

}