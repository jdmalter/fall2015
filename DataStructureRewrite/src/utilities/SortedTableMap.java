package utilities;

import java.util.Comparator;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class SortedTableMap<K extends Comparable<? super K>, V> extends
		AbstractSortedMap<K, V> {

	private ArrayList<MapEntry<K, V>> table;

	public SortedTableMap() {
		super();
		table = new ArrayList<MapEntry<K, V>>();
	}

	public SortedTableMap(Comparator<K> comparator) {
		super(comparator);
		table = new ArrayList<MapEntry<K, V>>();
	}

	private int findIndex(K key, int low, int high) {
		if (high < low)
			return high + 1;
		int mid = (high + low) / 2;
		int comparison = compare(key, table.get(mid).getKey());
		if (comparison == 0)
			return mid;
		else if (comparison < 0)
			return findIndex(key, low, mid - 1);
		else
			return findIndex(key, mid + 1, high);
	}

	private int findIndex(K key) {
		return findIndex(key, 0, table.size() - 1);
	}

	private Entry<K, V> safeEntry(int j) {
		if (j < 0 || j >= table.size())
			return null;
		return table.get(j);
	}

	@Override
	public int size() {
		return table.size();
	}

	@Override
	public V get(K key) {
		int j = findIndex(key);
		if (j == size() || compare(key, table.get(j).getKey()) != 0)
			return null;
		return table.get(j).getValue();
	}

	@Override
	public V put(K key, V value) {
		int j = findIndex(key);
		if (j < size() && compare(key, table.get(j).getKey()) == 0)
			return table.get(j).setValue(value);
		table.add(j, new MapEntry<K, V>(key, value));
		return null;
	}

	@Override
	public V remove(K key) {
		int j = findIndex(key);
		if (j == size() || compare(key, table.get(j).getKey()) != 0)
			return null;
		return table.remove(j).getValue();
	}

	@Override
	public Entry<K, V> firstEntry() {
		return safeEntry(0);
	}

	@Override
	public Entry<K, V> lastEntry() {
		return safeEntry(table.size() - 1);
	}

	@Override
	public Entry<K, V> ceilingEntry(K key) {
		return safeEntry(findIndex(key));
	}

	@Override
	public Entry<K, V> floorEntry(K key) {
		int j = findIndex(key);
		if (j == size() || !key.equals(table.get(j).getKey()))
			j--;
		return safeEntry(j);
	}

	@Override
	public Entry<K, V> lowerEntry(K key) {
		return safeEntry(findIndex(key) - 1);
	}

	@Override
	public Entry<K, V> higherEntry(K key) {
		int j = findIndex(key);
		if (j < size() && key.equals(table.get(j).getKey()))
			j++;
		return safeEntry(j);
	}

	private Iterable<Entry<K, V>> snapshot(int startIndex, K stop) {
		ArrayList<Entry<K, V>> buffer = new ArrayList<Entry<K, V>>();
		int j = startIndex;
		while (j < table.size()
				&& (stop == null || compare(stop, table.get(j).getKey()) > 0))
			buffer.add(buffer.size(), table.get(j++));
		return buffer;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return snapshot(0, null);
	}

	@Override
	public Iterable<Entry<K, V>> subMap(K key1, K key2) {
		return snapshot(findIndex(key1), key2);
	}

}