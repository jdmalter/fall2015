package secondutilities;

public class ListMap<K, V> extends AbstractMap<K, V> {

	private static class Entry<K, V> {
		private K key;
		private V value;

		private Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof Entry))
				return false;
			Entry<?, ?> other = (Entry<?, ?>) obj;
			return key == null ? other.key == null : key.equals(other.key);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			return prime + ((key == null) ? 0 : key.hashCode());
		}

	}

	private List<Entry<K, V>> list;

	public ListMap() {
		list = new ArrayList<Entry<K, V>>();
	}

	/**
	 * Slow method for decent sized maps.
	 */
	@Override
	public V put(K key, V value) {
		Entry<K, V> newEntry = new Entry<K, V>(key, value);
		int index = list.indexOf(newEntry);

		if (index < 0)
			list.add(list.size(), newEntry);
		else
			return list.set(index, newEntry).value;

		return null;
	}

	@Override
	public V get(K key) {
		Entry<K, V> entry = list.get(list.indexOf(new Entry<K, V>(key, null)));
		return entry == null ? null : entry.value;
	}

	@Override
	public boolean containsKey(K key) {
		return list.contains(new Entry<K, V>(key, null));
	}

	@Override
	public V remove(K key) {
		Entry<K, V> entry = list.remove(list
				.indexOf(new Entry<K, V>(key, null)));
		return entry == null ? null : entry.value;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Iterator<K> keyIterator() {
		return new Iterator<K>() {

			private Iterator<Entry<K, V>> iterator = list.iterator();

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public K next() {
				if (!hasNext())
					return null;
				return iterator.next().key;
			}

		};
	}

}