package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

	protected static class MapEntry<K, V> implements Entry<K, V> {
		private K key;
		private V value;

		public MapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		protected void setKey(K key) {
			this.key = key;
		}

		protected V setValue(V value) {
			V result = this.value;
			this.value = value;
			return result;
		}

	}

	private class KeyIterator implements Iterator<K> {

		private Iterator<Entry<K, V>> entries;

		public KeyIterator() {
			entries = entrySet().iterator();
		}

		@Override
		public boolean hasNext() {
			return entries.hasNext();
		}

		@Override
		public K next() {
			return entries.next().getKey();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private class KeyIterable implements Iterable<K> {

		@Override
		public Iterator<K> iterator() {
			return new KeyIterator();
		}

	}

	@Override
	public Iterable<K> keySet() {
		return new KeyIterable();
	}

	private class ValueIterator implements Iterator<V> {

		private Iterator<Entry<K, V>> entries;

		public ValueIterator() {
			entries = entrySet().iterator();
		}

		@Override
		public boolean hasNext() {
			return entries.hasNext();
		}

		@Override
		public V next() {
			return entries.next().getValue();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private class ValueIterable implements Iterable<V> {

		@Override
		public Iterator<V> iterator() {
			return new ValueIterator();
		}

	}

	@Override
	public Iterable<V> values() {
		return new ValueIterable();
	}

}