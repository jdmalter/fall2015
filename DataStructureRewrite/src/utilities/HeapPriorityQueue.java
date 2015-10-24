package utilities;

import java.util.Comparator;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class HeapPriorityQueue<K extends Comparable<? super K>, V> extends
		AbstractPriorityQueue<K, V> {

	private static class Node<K, V> implements Entry<K, V> {

		private K key;
		private V value;

		public Node(K key, V value) {
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

	}

	private static final int DEFAULT_CAPACITY = 31;

	private Node<K, V>[] heap;
	private int size;

	@SuppressWarnings("unchecked")
	public HeapPriorityQueue(Comparator<K> comparator) {
		super(comparator);
		heap = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
		// safe suppression for cast into generic type
	}

	private int parent(int j) {
		return (j - 1) / 2;
	}

	private int left(int j) {
		return 2 * j + 1;
	}

	private int right(int j) {
		return 2 * j + 2;
	}

	private boolean hasLeft(int j) {
		return left(j) < size;
	}

	private boolean hasRight(int j) {
		return right(j) < size;
	}

	private void swap(int i, int j) {
		Node<K, V> temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}

	private void upheap(int j) {
		while (j > 0) {
			int p = parent(j);
			if (compare(heap[j].getKey(), heap[p].getKey()) >= 0)
				break;
			swap(j, p);
			j = p;
		}
	}

	private void downheap(int j) {
		while (hasLeft(j)) {
			int leftIndex = left(j);
			int smallIndex = leftIndex;
			if (hasRight(j)) {
				int rightIndex = right(j);
				if (compare(heap[leftIndex].getKey(), heap[rightIndex].getKey()) > 0)
					smallIndex = rightIndex;
			}
			if (compare(heap[smallIndex].getKey(), heap[j].getKey()) >= 0)
				break;
			swap(j, smallIndex);
			j = smallIndex;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entry<K, V> insert(K key, V value) {
		if (size == heap.length - 1) {
			Node<K, V>[] old = heap;
			heap = (Node<K, V>[]) new Node[2 * heap.length + 1];
			// safe suppression for cast into generic type
			System.arraycopy(old, 0, heap, 0, size);
		}
		Node<K, V> insert = new Node<K, V>(key, value);
		heap[size] = insert;
		size++;
		upheap(size - 1);
		return insert;
	}

	@Override
	public Entry<K, V> min() {
		return size == 0 ? null : heap[0];
	}

	@Override
	public Entry<K, V> removeMin() {
		if (size == 0)
			return null;
		Entry<K, V> result = heap[0];
		swap(0, size - 1);
		heap[size - 1] = null;
		size--;
		downheap(0);
		return result;
	}

}