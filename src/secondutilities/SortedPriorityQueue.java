package secondutilities;

public class SortedPriorityQueue<K extends Comparable<K>, V> implements
		PriorityQueue<K, V> {

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

	private int size;
	private Comparator<K> comparator;
	private Entry<K, V> head, tail;

	public SortedPriorityQueue(Comparator<K> comparator) {
		this.comparator = comparator == null ? (left, right) -> {
			if (left == null)
				return right != null ? -1 : 0;
			return left.compareTo(right);
		} : comparator;
	}

	/**
	 * Very slow
	 */
	@Override
	public void insert(K key, V value) {
		Entry<K, V> entry = new Entry<K, V>(key, value);
		if (size == 0) {
			head = entry;
			tail = head;
		} else {
			if (comparator.compare(key, head.key) > 0) {
				entry.next = head;
				head.prev = entry;
				head = entry;
			} else if (comparator.compare(key, tail.key) <= 0) {
				entry.prev = tail;
				tail.next = entry;
				tail = entry;
			} else {
				Entry<K, V> current = head;
				while (current != null
						&& comparator.compare(key, current.key) < 0)
					current = current.next;
				entry.prev = current.prev;
				entry.next = current;
				entry.prev.next = entry;
				entry.next.prev = entry;
			}
		}
		size++;
	}

	/**
	 * Very fast, best speed
	 */
	@Override
	public K peekKey() {
		if (size == 0)
			return null;
		return head.key;
	}

	/**
	 * Very fast, best speed
	 */
	@Override
	public V peekValue() {
		if (size == 0)
			return null;
		return head.value;
	}

	/**
	 * Very fast, best speed
	 */
	@Override
	public V remove() {
		if (size == 0)
			return null;
		V result = head.value;
		head = head.next;
		if (head != null)
			head.prev = null;
		size--;
		return result;
	}

	@Override
	public int size() {
		return size;
	}

}