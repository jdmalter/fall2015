package utilities;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {

	private UnsortedTableMap<K, V>[] table;

	public ChainHashMap() {
		super();
	}

	public ChainHashMap(int capacity) {
		super(capacity);
	}

	public ChainHashMap(int capacity, int prime) {
		super(capacity, prime);
	}

	@SuppressWarnings("unchecked")
	protected void createTable() {
		table = (UnsortedTableMap<K, V>[]) new UnsortedTableMap[capacity];
	}

	@Override
	protected V bucketGet(int hash, K key) {
		UnsortedTableMap<K, V> bucket = table[hash];
		if (bucket == null)
			return null;
		return bucket.get(key);
	}

	@Override
	protected V bucketPut(int hash, K key, V value) {
		UnsortedTableMap<K, V> bucket = table[hash];
		if (bucket == null)
			bucket = table[hash] = new UnsortedTableMap<K, V>();
		int oldSize = bucket.size();
		V result = bucket.put(key, value);
		size += (bucket.size() - oldSize);
		return result;
	}

	@Override
	protected V bucketRemove(int hash, K key) {
		UnsortedTableMap<K, V> bucket = table[hash];
		if (bucket == null)
			return null;
		int oldSize = bucket.size();
		V result = bucket.remove(key);
		size -= (oldSize - bucket.size());
		return result;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> buffer = new ArrayList<Entry<K, V>>();
		for (int i = 0; i < capacity; i++)
			if (table[i] != null) {
				Iterator<Entry<K, V>> entries = table[i].entrySet().iterator();
				int j = 0;
				while (entries.hasNext())
					buffer.add(j++, entries.next());
			}
		return buffer;
	}

}