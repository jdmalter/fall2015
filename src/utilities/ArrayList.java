package utilities;

import java.util.NoSuchElementException;

/**
 * @author Jacob Malter learning from Data Structures and Algorithms in Java
 */
public class ArrayList<E> implements List<E> {

	private class ArrayIterator implements Iterator<E> {

		private int counter;
		private boolean removable;

		public ArrayIterator() {

		}

		@Override
		public boolean hasNext() {
			return counter < ArrayList.this.size();
		}

		@Override
		public E next() {
			if (counter == ArrayList.this.size())
				throw new NoSuchElementException();
			removable = true;
			return data[counter++];
		}

		@Override
		public void remove() {
			if (!removable)
				throw new IllegalStateException();
			ArrayList.this.remove(counter - 1);
			counter--;
			removable = false;
		}

	}

	private static final int CAPACITY = 16;

	private E[] data;
	private int size;

	public ArrayList() {
		this(CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		data = (E[]) new Object[capacity];
	}

	private void checkIndex(int index, int limit) {
		if (index < 0 || index >= limit)
			throw new IndexOutOfBoundsException();
	}

	private void resize(int capacity) {
		@SuppressWarnings("unchecked")
		E[] newData = (E[]) new Object[capacity];
		for (int k = 0; k < size(); k++)
			newData[k] = data[k];
		data = newData;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public E get(int index) {
		checkIndex(index, size());
		return data[index];
	}

	@Override
	public E set(int index, E element) {
		checkIndex(index, size());
		E result = data[index];
		data[index] = element;
		return result;
	}

	@Override
	public void add(int index, E element) {
		checkIndex(index, size() + 1);
		if (size() == data.length - 1)
			resize(2 * data.length + 1);
		for (int k = size(); k >= index; k--)
			data[k + 1] = data[k];
		data[index] = element;
		size++;
	}

	@Override
	public E remove(int index) {
		checkIndex(index, size());
		E result = data[index];
		for (int k = index; k < size() - 1; k++)
			data[k] = data[k + 1];
		data[size - 1] = null;
		size--;
		return result;
	}

	@Override
	public Iterator<E> iterator() {
		return new ArrayIterator();
	}

}