package utilities;

import java.util.Comparator;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public abstract class AbstractPriorityQueue<K extends Comparable<? super K>, V>
		implements PriorityQueue<K, V> {

	private Comparator<K> comparator;

	protected AbstractPriorityQueue(Comparator<K> comparator) {
		if (comparator == null)
			comparator = Comparator.nullsFirst(Comparator.naturalOrder());
		this.comparator = comparator;
	}

	protected int compare(Entry<K, V> one, Entry<K, V> two) {
		return comparator.compare(one.getKey(), two.getKey());
	}

}