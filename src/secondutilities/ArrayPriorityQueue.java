package secondutilities;

public class ArrayPriorityQueue<K extends Comparable<K>, V> implements
		PriorityQueue<K, V> {

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

	private static final int INITIAL = 31;

	private int size;
	private Comparator<K> comparator;
	private Entry<K, V>[] entries;

	public ArrayPriorityQueue() {
		this(null);
	}

	@SuppressWarnings("unchecked")
	public ArrayPriorityQueue(Comparator<K> comparator) {
		this.comparator = comparator == null ? (left, right) -> {
			if (left == null)
				return right != null ? -1 : 0;
			return left.compareTo(right);
		} : comparator;
		entries = (Entry<K, V>[]) new Entry<?, ?>[INITIAL];
	}

	@Override
	public void insert(K key, V value) {
		if (size == entries.length - 1) {
			@SuppressWarnings("unchecked")
			Entry<K, V>[] newEntries = (Entry<K, V>[]) new Entry[(2 * entries.length) + 1];

			for (int i = 0; i < entries.length; i++)
				newEntries[i] = entries[i];
			entries = newEntries;
		}

		entries[size++] = new Entry<K, V>(key, value);

		int index = size - 1;
		while (index > 0) {
			int parent = (index - 1) / 2;
			if (comparator.compare(entries[index].key, entries[parent].key) < 0)
				break;
			Entry<K, V> temp = entries[index];
			entries[index] = entries[parent];
			entries[parent] = temp;

			index = parent;
		}
	}

	@Override
	public V peek() {
		return size == 0 ? null : entries[0].value;
	}

	@Override
	public V remove() {
		if (size == 0)
			return null;

		V result = entries[0].value;
		entries[0] = entries[size - 1];

		entries[--size] = null;

		int index = 0;
		while (2 * index + 1 < size) {
			int left = 2 * index + 1;
			int large = left;
			if (left + 1 < size) {
				int right = left + 1;
				if (comparator.compare(entries[left].key, entries[right].key) < 0)
					large = right;
			}
			if (comparator.compare(entries[index].key, entries[large].key) > 0)
				break;

			Entry<K, V> temp = entries[index];
			entries[index] = entries[large];
			entries[large] = temp;

			index = large;
		}
		return result;
	}

	@Override
	public int size() {
		return size;
	}

}