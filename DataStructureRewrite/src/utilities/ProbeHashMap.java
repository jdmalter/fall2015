package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {

	private MapEntry<K, V>[] table;
	private MapEntry<K, V> DEFUNCT;

	public ProbeHashMap() {
		super();
		DEFUNCT = new MapEntry<K, V>(null, null);
	}

	public ProbeHashMap(int capacity) {
		super(capacity);
		DEFUNCT = new MapEntry<K, V>(null, null);
	}

	public ProbeHashMap(int capacity, int prime) {
		super(capacity, prime);
		DEFUNCT = new MapEntry<K, V>(null, null);
	}

	private boolean isAvailable(int j) {
		return (table[j] == null || table[j] == DEFUNCT);
	}

	private int findSlot(int hash, K key) {
		int avail = -1;
		int j = hash;
		do {
			if (isAvailable(j)) {
				if (avail == -1)
					avail = j;
				if (table[j] == null)
					break;
			} else if (table[j].getKey().equals(key))
				return j;
			j = (j + 1) % capacity;
		} while (j != hash);
		return -(avail + 1);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createTable() {
		table = (MapEntry<K, V>[]) new MapEntry[capacity];
	}

	@Override
	protected V bucketGet(int hash, K key) {
		int j = findSlot(hash, key);
		if (j < 0)
			return null;
		return table[j].getValue();
	}

	@Override
	protected V bucketPut(int hash, K key, V value) {
		int j = findSlot(hash, key);
		if (j >= 0)
			return table[j].setValue(value);
		table[-(j + 1)] = new MapEntry<K, V>(key, value);
		size++;
		return null;
	}

	@Override
	protected V bucketRemove(int hash, K key) {
		int j = findSlot(hash, key);
		if (j < 0)
			return null;
		V result = table[j].getValue();
		table[j] = DEFUNCT;
		size--;
		return result;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> buffer = new ArrayList<Entry<K, V>>();
		for (int i = 0, j = 0; i < capacity; i++)
			if (!isAvailable(i))
				buffer.add(j++, table[i]);
		return buffer;
	}

}