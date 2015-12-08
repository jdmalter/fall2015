package secondutilities;

public class IdentityLinkedHashMap<K, V> extends AbstractMap<K, V> {

	private static class Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> prev, next, lower;

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
			return key == other.key;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			return prime + ((key == null) ? 0 : key.hashCode());
		}

	}

	private static final int INITIAL = 31;
	private static final int PRIME = 2147483647;
	private static final float LOAD = 0.75f;

	private int size;
	private Entry<K, V> head, tail;
	private Entry<K, V>[] buckets;

	@SuppressWarnings("unchecked")
	public IdentityLinkedHashMap() {
		buckets = (Entry<K, V>[]) new Entry<?, ?>[INITIAL];
	}

	@Override
	public V put(K key, V value) {
		rehash();
		int index = hash(key);

		if (buckets[index] == null) {
			buckets[index] = new Entry<K, V>(key, value);
			link(buckets[index]);
			size++;

		} else {
			Entry<K, V> current = buckets[index];
			while (current != null) {

				if (current.key == key) {
					V result = current.value;
					current.value = value;
					return result;
				}
				current = current.lower;
			}

			Entry<K, V> newEntry = new Entry<K, V>(key, value);
			newEntry.lower = buckets[index];
			buckets[index] = newEntry;
			link(newEntry);
			size++;
		}

		return null;
	}

	@Override
	public V get(K key) {
		Entry<K, V> current = buckets[hash(key)];
		while (current != null) {
			if (current.key == key)
				return current.value;
			current = current.lower;
		}
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		Entry<K, V> current = buckets[hash(key)];
		while (current != null) {
			if (current.key == key)
				return true;
			current = current.lower;
		}
		return false;
	}

	@Override
	public V remove(K key) {
		int index = hash(key);

		Entry<K, V> prev = null;
		Entry<K, V> current = buckets[index];
		while (current != null) {
			if (current.key == key) {
				V result = current.value;

				if (prev != null)
					prev.lower = prev.lower.lower;
				else
					buckets[index] = buckets[index].lower;

				delink(current);
				current = null;
				size--;
				return result;
			}
			prev = current;
			current = current.lower;
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
			cursor.lower = buckets[index];
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