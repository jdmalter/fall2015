package utilities;

import java.util.NoSuchElementException;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class UnsortedMapTable<K, V> extends AbstractMap<K, V> {

	private ArrayList<MapEntry<K, V>> table;

	public UnsortedMapTable() {
		table = new ArrayList<MapEntry<K, V>>();
	}

	private int findIndex(K key) {
		int size = table.size();
		for (int i = 0; i < size; i++)
			if (table.get(i).getKey().equals(key))
				return i;
		return -1;
	}

	@Override
	public int size() {
		return table.size();
	}

	@Override
	public V get(K key) {
		int j = findIndex(key);
		if (j == -1)
			return null;
		return table.get(j).getValue();
	}

	@Override
	public V put(K key, V value) {
		int j = findIndex(key);
		int size = table.size();
		if (j == -1) {
			table.add(size, new MapEntry<K, V>(key, value));
			return null;
		} else
			return table.get(j).setValue(value);
	}

	@Override
	public V remove(K key) {
		int j = findIndex(key);
		int size = table.size();
		if (j == -1)
			return null;
		V result = table.get(j).getValue();
		if (j != size - 1)
			table.set(j, table.get(size - 1));
		table.remove(size - 1);
		return result;
	}

	private class EntryIterator implements Iterator<Entry<K, V>> {

		private int counter;

		@Override
		public boolean hasNext() {
			return counter < table.size();
		}

		@Override
		public Entry<K, V> next() {
			if (counter == table.size())
				throw new NoSuchElementException();
			return table.get(counter++);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private class EntryIterable implements Iterable<Entry<K, V>> {

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new EntryIterator();
		}

	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return new EntryIterable();
	}

}