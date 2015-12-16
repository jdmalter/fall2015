package secondutilities;

public class UnsortedPriorityQueue<K extends Comparable<K>, V> implements
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

	public UnsortedPriorityQueue(Comparator<K> comparator) {
		this.comparator = comparator == null ? (left, right) -> {
			if (left == null)
				return right != null ? -1 : 0;
			return left.compareTo(right);
		} : comparator;
	}

	/**
	 * Very fast, best speed
	 */
	@Override
	public void insert(K key, V value) {
		Entry<K, V> entry = new Entry<K, V>(key, value);
		if (size == 0) {
			head = entry;
			tail = head;
		} else {
			entry.prev = tail;
			tail.next = entry;
			tail = entry;
		}
		size++;
	}

	/**
	 * Very slow
	 */
	@Override
	public V peek() {
		if (size == 0)
			return null;
		else if (size == 1)
			return head.value;
		else {
			Entry<K, V> max = head;
			Entry<K, V> current = head.next;
			while (current != null) {
				if (comparator.compare(current.key, max.key) > 0)
					max = current;
				current = current.next;
			}
			return max.value;
		}
	}

	/**
	 * Very slow
	 */
	@Override
	public V remove() {
		if (size == 0)
			return null;
		else if (size == 1) {
			V result = head.value;
			head = null;
			tail = null;
			size--;
			return result;
		} else {
			Entry<K, V> max = head;
			Entry<K, V> current = head.next;
			while (current != null) {
				if (comparator.compare(current.key, max.key) > 0)
					max = current;
				current = current.next;
			}
			if (max.prev == null) {
				head = head.next;
				head.prev = null;
			} else if (max.next == null) {
				tail = tail.prev;
				tail.next = null;
			} else {
				max.prev.next = max.next;
				max.next.prev = max.prev;
			}
			current = null;
			size--;
			return max.value;
		}
	}

	@Override
	public int size() {
		return size;
	}

}