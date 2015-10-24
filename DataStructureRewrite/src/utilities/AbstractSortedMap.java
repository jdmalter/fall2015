package utilities;

import java.util.Comparator;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public abstract class AbstractSortedMap<K extends Comparable<? super K>, V>
		extends AbstractMap<K, V> implements SortedMap<K, V> {

	private Comparator<K> comparator;

	public AbstractSortedMap() {
		this(null);
	}

	public AbstractSortedMap(Comparator<K> comparator) {
		if (comparator == null)
			comparator = Comparator.nullsFirst(Comparator.naturalOrder());
		this.comparator = comparator;
	}

	protected int compare(K one, K two) {
		return comparator.compare(one, two);
	}

}