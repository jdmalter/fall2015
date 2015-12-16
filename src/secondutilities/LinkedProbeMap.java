package secondutilities;

public class LinkedProbeMap<K, V> implements Map<K, V> {

	private static class Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> prev, next;

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

	private static final int INITIAL = 31;
	private static final int PRIME = 2147483647;
	private static final float LOAD = 0.67f;
	private final Entry<K, V> AVAILABLE = new Entry<K, V>(null, null);

	private int size;
	private Entry<K, V> head, tail;
	private Entry<K, V>[] buckets;

	@SuppressWarnings("unchecked")
	public LinkedProbeMap() {
		buckets = (Entry<K, V>[]) new Entry<?, ?>[INITIAL];
	}

	/**
	 * Slower than LinkedHashMap
	 */
	@Override
	public V put(K key, V value) {
		rehash();
		int index = hash(key);

		while (buckets[index] == AVAILABLE
				&& buckets[index] != null
				&& (buckets[index].key != null ? buckets[index].key.equals(key)
						: key == null)) {
			index = (index + 1) % buckets.length;
		}

		if (buckets[index] != null
				&& (buckets[index].key != null ? buckets[index].key.equals(key)
						: key == null)) {
			V result = buckets[index].value;
			buckets[index].value = value;
			return result;
		}

		buckets[index] = new Entry<K, V>(key, value);
		link(buckets[index]);
		size++;
		return null;
	}

	/**
	 * Slower than LinkedHashMap
	 */
	@Override
	public V get(K key) {
		int index = hash(key);
		int count = 0;
		while (buckets[index] == AVAILABLE
				&& buckets[index] != null
				&& (buckets[index].key != null ? buckets[index].key.equals(key)
						: key == null) && count < buckets.length) {
			index = (index + 1) % buckets.length;
			count++;
		}

		if (buckets[index] != null
				&& (buckets[index].key != null ? buckets[index].key.equals(key)
						: key == null))
			return buckets[index].value;

		return null;
	}

	/**
	 * Slower than LinkedHashMap
	 */
	@Override
	public boolean containsKey(K key) {
		int index = hash(key);
		int count = 0;
		while (buckets[index] == AVAILABLE
				&& buckets[index] != null
				&& (buckets[index].key != null ? buckets[index].key.equals(key)
						: key == null) && count < buckets.length) {
			index = (index + 1) % buckets.length;
			count++;
		}

		if (buckets[index] != null
				&& (buckets[index].key != null ? buckets[index].key.equals(key)
						: key == null))
			return true;

		return false;
	}

	/**
	 * Slower than LinkedHashMap
	 */
	@Override
	public V remove(K key) {
		int index = hash(key);
		int count = 0;
		while (buckets[index] == AVAILABLE
				&& buckets[index] != null
				&& (buckets[index].key != null ? buckets[index].key.equals(key)
						: key == null) && count < buckets.length) {
			index = (index + 1) % buckets.length;
			count++;
		}

		if (buckets[index] != null
				&& (buckets[index].key != null ? buckets[index].key.equals(key)
						: key == null)) {
			V result = buckets[index].value;
			buckets[index] = null;
			buckets[index] = AVAILABLE;
			delink(buckets[index]);
			size--;
			return result;
		}

		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<K> keyIterator() {
		return new Iterator<K>() {

			private Entry<K, V> cursor = head;

			@Override
			public boolean hasNext() {
				return cursor != null;
			}

			@Override
			public K next() {
				if (!hasNext())
					return null;
				K result = cursor.key;
				cursor = cursor.next;
				return result;
			}

		};
	}

	@SuppressWarnings("unchecked")
	private void rehash() {
		if (size < buckets.length * LOAD)
			return;
		Entry<K, V> cursor = head;
		buckets = (Entry<K, V>[]) new Entry<?, ?>[(2 * buckets.length) + 1];

		while (cursor != null) {
			int index = hash(cursor.key);
			while (buckets[index] != null)
				index = (index + 1) % buckets.length;
			buckets[index] = cursor;
			cursor = cursor.next;
		}
	}

	private int hash(K key) {
		int hash = key == null ? 0 : key.hashCode();
		int result = (PRIME * hash) % buckets.length;
		if (result < 0)
			result *= -1;
		return result;
	}

	private void link(Entry<K, V> node) {
		node.prev = null;
		node.next = null;
		node.prev = tail;

		if (tail == null)
			head = node;
		else
			tail.next = node;

		tail = node;
	}

	private void delink(Entry<K, V> node) {
		if (head == node)
			head = node.next;
		if (tail == node)
			tail = tail.prev;

		if (node.next != null)
			node.next.prev = node.prev;
		if (node.prev != null)
			node.prev.next = node.next;

		node.prev = null;
		node.next = null;
	}

}