package utilities;

import java.util.Random;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {

	protected int size;
	protected int capacity;
	private int prime;
	private long scale, shift;

	public AbstractHashMap() {
		this(17);
	}

	public AbstractHashMap(int capacity) {
		this(capacity, 109345121);
	}

	public AbstractHashMap(int capacity, int prime) {
		this.prime = prime;
		this.capacity = capacity;
		Random rand = new Random();
		scale = rand.nextInt(prime - 1) + 1;
		shift = rand.nextInt(prime);
		createTable();
	}

	private int hashValue(K key) {
		return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
	}

	private void resize(int newCapacity) {
		ArrayList<Entry<K, V>> buffer = new ArrayList<Entry<K, V>>(size);
		Iterator<Entry<K, V>> iterator = entrySet().iterator();
		while (iterator.hasNext())
			buffer.add(buffer.size(), iterator.next());
		capacity = newCapacity;
		createTable();
		size = 0;
		Iterator<Entry<K, V>> bufferIterator = buffer.iterator();
		while (bufferIterator.hasNext()) {
			Entry<K, V> current = bufferIterator.next();
			put(current.getKey(), current.getValue());
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public V get(K key) {
		return bucketGet(hashValue(key), key);
	}

	@Override
	public V put(K key, V value) {
		V result = bucketPut(hashValue(key), key, value);
		if (size > capacity / 2)
			resize(2 * capacity + 1);
		return result;
	}

	@Override
	public V remove(K key) {
		return bucketRemove(hashValue(key), key);
	}

	protected abstract void createTable();

	protected abstract V bucketGet(int hash, K key);

	protected abstract V bucketPut(int hash, K key, V value);

	protected abstract V bucketRemove(int hash, K key);

}